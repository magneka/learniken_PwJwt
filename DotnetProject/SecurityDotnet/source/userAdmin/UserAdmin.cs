namespace SecurityDotnet.source.userAdmin;

public interface IUserAdmin
{
    UsersDTO? Login(string email, string password);
    List<TravelDTO>? GetTravels(string userId, string keyword);
    string RegisterUser(string email, string name, string password);
};

public class AspnetUsersDto
{
    public List<UsersDTO>? aspnetusers { get; set; }
}

public class UserAdmin : IUserAdmin
{
    private IMsIdentity _MsIdentity;

    public UserAdmin(IMsIdentity MsIdentity)
    {
        _MsIdentity = MsIdentity;
    }

    public UsersDTO? Login(string email, string password)
    {
        AspnetUsersDto? usersobj = JsonConvert.DeserializeObject<AspnetUsersDto>(File.ReadAllText("./database/aspnetUsers.json"));
        UsersDTO? user = usersobj?.aspnetusers?.FirstOrDefault(x => x.UserName == email);

        if (user == null)
            return null;

        if (_MsIdentity.ValidatePassword(password, user.PasswordHash))
            return user;

        return null;
    }

    public List<TravelDTO>? GetTravels(string userId, string keyword)
    {
        List<TravelDTO>? travels = JsonConvert.DeserializeObject<List<TravelDTO>>(File.ReadAllText("./database/travels.json"));
        List<TravelDTO>? result = travels?
            .FindAll(t => t.UserId == userId && t.From.Name.ToUpper().Contains(keyword.ToUpper()))
            .ToList();
        return result;
    }

    public string RegisterUser(string email, string name, string password)
    {

        var passwordHash = _MsIdentity.CreatePasswordHash(password);
        var newUser = new UsersDTO
        {
            Id = Guid.NewGuid().ToString(),
            FullName = name,
            UserName = email,
            PasswordHash = passwordHash
        };

        AspnetUsersDto? usersobj = JsonConvert.DeserializeObject<AspnetUsersDto>(File.ReadAllText("./database/aspnetUsers.json"));
        usersobj?.aspnetusers?.Add(newUser);

        File.WriteAllText(@"./database/aspnetUsers2.json", JsonConvert.SerializeObject(usersobj));

        return "Ok";
    }
}
