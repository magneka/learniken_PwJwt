package uc.no.identity.datalayer.travel;

import java.util.ArrayList;

import uc.no.identity.datalayer.users.UsersDto;

public interface ITravelRepository {

    ArrayList<TravelDto> getTravelsForUser(String userId, String keyword);
}