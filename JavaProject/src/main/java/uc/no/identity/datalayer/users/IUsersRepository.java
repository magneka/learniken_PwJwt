package uc.no.identity.datalayer.users;

public interface IUsersRepository {

    UsersDto getUserByEmail(String email);

    UsersDto saveUser (UsersDto user);

}