namespace SecurityDotnet.source.jwttoken;

public interface IJwtToken
{
    JwtSecurityToken? GenerateJwtToken(UsersDTO user);
};

public class JwtToken : IJwtToken
{
    // ***********************************************************
    // Denne er nøkkelen for hackere! ikke sjekk inn noe som dette
    // ***********************************************************
    const string TOKENSECRET = "REVEN_OVERRASKER_GRISEN";
    const string ISSUER = "faggruppe.uc.no";
    const string AUDIENCE = "uc.no";
    const int DURATION = 10;

    private static List<string> GetSecurityValidAlgorithms()
    {
        return new List<string> {
                    SecurityAlgorithms.HmacSha512Signature,
                    SecurityAlgorithms.HmacSha256Signature,
                    SecurityAlgorithms.HmacSha256,
                    SecurityAlgorithms.HmacSha512
                };
    }

    /// <summary>
    /// Kjøres fra startup for å legge til jwt authentication        
    /// </summary>
    /// <param name="builder"></param>
    public static void AddJWTAuthentication(WebApplicationBuilder builder)
    {
        var validAlgorithms = GetSecurityValidAlgorithms();
        builder.Services.AddAuthentication(options =>
        {
            options.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
            options.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
            options.DefaultScheme = JwtBearerDefaults.AuthenticationScheme;
        }).AddJwtBearer(o =>
        {
            o.TokenValidationParameters = new TokenValidationParameters
            {
                ValidIssuer = ISSUER,
                ValidAudience = AUDIENCE,
                IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(TOKENSECRET)),
                ValidateIssuer = true,
                ValidateAudience = true,
                ValidateLifetime = false,
                ValidateIssuerSigningKey = true,
                ValidAlgorithms = validAlgorithms
            };
        });
        // End Add JWT token authentication
    }

    /// <summary>
    /// Genererer et gyldig JWT Token
    /// </summary>
    /// <param name="user"></param>
    /// <returns></returns>
    public JwtSecurityToken? GenerateJwtToken(UsersDTO user)
    {
        if (user != null)
        {
            // Henter konstanter - I real life ikke bruk konstanter!
            string issuer = ISSUER;
            string audience = AUDIENCE;
            string key = TOKENSECRET;  // Denne er nøkkel til hacking! ikke sjekk inn
            int tokenMinutes = DURATION;

            var keyBytes = Encoding.UTF8.GetBytes(key);
            var theKey = new SymmetricSecurityKey(keyBytes);
            var creds = new SigningCredentials(theKey, SecurityAlgorithms.HmacSha512Signature);

            // The claims, info available to anyone who can read the token             
            var claimsList = new List<Claim>();
            claimsList.Add(new Claim(JwtRegisteredClaimNames.Email, user.UserName));
            claimsList.Add(new Claim(JwtRegisteredClaimNames.Name, $"{user.FullName}"));
            claimsList.Add(new Claim("UserId", user.Id));
            claimsList.Add(new Claim("Language", user.Language));

            if (user.Role.Equals("Administrator"))
            {
                claimsList.Add(new Claim("role", "Administrator"));
            }
            else
            {
                claimsList.Add(new Claim("role", "User"));
            }

            JwtSecurityToken token = new JwtSecurityToken(
                issuer,
                audience,
                claimsList,
                expires: DateTime.Now.AddMinutes(tokenMinutes),
                signingCredentials: creds);

            return token;
        }

        return null;
    }
}