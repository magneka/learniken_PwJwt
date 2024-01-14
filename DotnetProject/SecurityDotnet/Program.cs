var builder = WebApplication.CreateBuilder(new WebApplicationOptions
{
    Args = args,
    WebRootPath = "wwwroot"
});

// Add services to the container.
builder.Services.AddControllers();
JwtToken.AddJWTAuthentication(builder);
builder.Services.AddScoped<IJwtToken, JwtToken>();
builder.Services.AddScoped<IMsIdentity, MsIdentity>();
builder.Services.AddScoped<IUserAdmin, UserAdmin>();
var app = builder.Build();

// Configure the HTTP request pipeline.
app.UseHttpsRedirection();
app.UseAuthentication();
app.UseStaticFiles();
app.MapControllers();

app.MapPost("/login", (SignInModel model) =>
{
    var scope = app.Services.CreateScope().ServiceProvider;
    UsersDTO? loginUser = scope
        .GetRequiredService<IUserAdmin>()
        .Login(model.email, model.password);

    if (loginUser != null)
    {
        var token = scope
            .GetRequiredService<IJwtToken>()
            .GenerateJwtToken(loginUser);
        
        if (token != null)
            return Results.Ok(new
            {
                token = new JwtSecurityTokenHandler().WriteToken(token),
            });
    }   
    return Results.BadRequest("Invalid user or password");
});

app.MapGet("/check",
[Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
() => "Your are good to go!.");

app.MapPost("/travels",
[Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
(HttpContext ctx, TravelSearchModel jsonParams) => {

    var identity = ctx.User.Identity as ClaimsIdentity;
    var userIdClaim = identity?.FindFirst("UserId");
    var userId = userIdClaim?.Value;
    if (userId != null) {
        var scope = app.Services.CreateScope().ServiceProvider;
        IUserAdmin userAdmin = scope.GetRequiredService<IUserAdmin>();
        var result = userAdmin.GetTravels(userId, jsonParams.destinasjon);
        return Results.Ok(result);
    }  
    return Results.NotFound();  
});

app.MapPost("/register",
(CreateUserModel jsonParams) => {

    var email = jsonParams.Email;
    var name = jsonParams.Name;
    var password = jsonParams.Password;

    if (email != null && name != null && password != null) {
        var scope = app.Services.CreateScope().ServiceProvider;
        IUserAdmin userAdmin = scope.GetRequiredService<IUserAdmin>();
        var result = userAdmin.RegisterUser(email, name, password);
        return Results.Ok(result);
    }  
    return Results.NotFound();  
});

app.Run();