namespace ScuityDotnetTests;

public class UnitTest1
{

    [Fact]
    public void FremOgtilbke()
    {        
        byte[] pass = Encoding.UTF8.GetBytes("Hi");
        string encoded = Convert.ToBase64String(pass);
        string org = Encoding.UTF8.GetString(pass);

        Assert.True(encoded == "SGk=");
        Assert.True(pass[0] == 72);
        Assert.True(pass[1] == 105);
        Assert.True(org == "Hi");
    }

    [Fact]
    public void FremOgtilbke2()
    {        
        var utf8 = new UTF8Encoding();
        byte[] pass = utf8.GetBytes("Hi");
        string encoded = Convert.ToBase64String(pass);
        string org = utf8.GetString(pass);

        Assert.True(encoded == "SGk=");
        Assert.True(pass[0] == 72);
        Assert.True(pass[1] == 105);
        Assert.True(org == "Hi");
    }   
}