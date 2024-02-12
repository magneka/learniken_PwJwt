package uc.no.identity.datalayer.ucsql;

public class ucDbFields {

    private String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    private String sqlType;

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    private Boolean isId;

    public Boolean getIsId() {
        return isId;
    }

    public void setIsId(Boolean isId) {
        this.isId = isId;
    }

    private Boolean nullAllowed;

    public Boolean getNullAllowed() {
        return nullAllowed;
    }

    public void setNullAllowed(Boolean nullAllowed) {
        this.nullAllowed = nullAllowed;
    }
}
