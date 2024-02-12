package uc.no.other;

import java.util.Date;

import uc.no.identity.annotations.Id;
import uc.no.identity.annotations.NotNull;

@Table(name = "AllSorts")
public class Allsorts {

    @Id
    @NotNull
    private int id;

    private String navn;

    private Date dato;

    private Float belop;
    
}
