namespace ScuityDotnetTests;
public class MsIdentityTest
{
    [Fact]
    public void CreatePasswordHash01()
    {
        var msIdentity = new MsIdentity();
        var hash = msIdentity.CreatePasswordHash("DotnetIsC00l!");
    }

    [Fact]
    public void ValidatePassword()
    {
        var msIdentity = new MsIdentity();
        var pwIsCorrect = msIdentity.ValidatePassword(
            "DotnetIsC00l!",
            "AQAAAAEAACcQAAAAENPhYEdKgpnnTMR7E/q7I4g0zHJuz0QAzUSmV1JsXIjXndY8vLhhXjUJ6LqoOIRd0g==");

        Assert.True(pwIsCorrect);
    }

    [Fact]
    public void ValidatePasswordFromJs()
    {
        // Sjekk en hash generert med Javascript programmet
        var msIdentity = new MsIdentity();
        var pwIsCorrect = msIdentity.ValidatePassword(
            "Secret01!",
            "AQAAAAEAACcQAAAAEHJ76TcZq46JvLgCzCOWf55uupFZesz7ZUg4io6EIrQb5n/2pQM4dPTexgJmeHaZsQ==");

        Assert.True(pwIsCorrect);
    }
}