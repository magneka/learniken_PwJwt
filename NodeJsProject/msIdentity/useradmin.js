// Modul for å validere passord
const bcrypt = require('bcrypt');
const { readFileSync, writeFileSync } = require('fs');
const { newUserHashPassword, validatePassword } = require('./msIdentity');

// TODO Correct this filename after checking out
const databaseFolder = '../data/';
const databaseFile = 'aspnetUsers.json';
const travelsFile = 'travels.json'

exports.loadDatabase = loadDatabase = () => {
    const data = readFileSync(databaseFolder + databaseFile);
    jsonDB = JSON.parse(data)
    return jsonDB
}

exports.saveDatabase = saveDatabase = async (dbAsJson) => {
    let dbAsString = JSON.stringify(dbAsJson, null, '\t')
    const tt = writeFileSync(databaseFolder + databaseFile, dbAsString)
}

exports.getTravels = (userId, keyword) => {
    const jsonData = readFileSync(databaseFolder + travelsFile);
    data = JSON.parse(jsonData)
    let travels = data.filter(a => 
               a.UserId == userId &&
               (a.From.name.toUpperCase().includes(keyword.toUpperCase()) ||
               (a.To.name.toUpperCase().includes(keyword.toUpperCase()))))
    return travels
}

exports.getUser = getUser = (email) => {
    let db = this.loadDatabase()

    const user = db.aspnetusers.find(user => user.UserName === email);
    if (user) console.log(user)
    return user
}

exports.validateUser = validateUser = (email, password) => {
    try {        
        let user = getUser(email)        
        if (!user) 
            return false        
        let result = validatePassword(password, user.PasswordHash)                
        return result
    } catch (error) {
        console.log(error)
        return error
    } 
}

exports.registerUser = registerUser = (userName, fullName, passWord) => {
    // TODO hva om brukeren finnes
    try {
        let db = this.loadDatabase()
        let passwordHash = newUserHashPassword(passWord)
        
        let newUser = {
            "UserName": userName,
            "FullName": fullName,
            "PasswordHash": passwordHash
        }
        db.aspnetusers.push (newUser)
        console.log(db.aspnetusers)
        saveDatabase(db)
        return 'OK'
    } catch (error) {
        return error
    }
}