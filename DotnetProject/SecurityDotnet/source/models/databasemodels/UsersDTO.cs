namespace SecurityDotnet.source.models
{
	public class UsersDTO
	{
        public string Id { get; set; } = "";        
        public string UserName { get; set; } = "";
        public string FullName { get; set; } = "";
        public string Role { get; set; } = "User";
        public string Language { get; set; } = "nbNO";
        public string PasswordHash { get; set; }
    }
}