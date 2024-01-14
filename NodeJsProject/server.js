const express = require('express')
const bodyParser = require('body-parser')
const { getUser, validateUser, registerUser, getTravels } = require('./msIdentity/useradmin')
const { createJwtToken, validateJwtToken } = require('./jwtToken/jwtToken')

// Router
// https://blog.stackademic.com/mastering-jwt-authentication-in-express-js-bfca2ea068fa

// Middleware logger
const myLogger = function (req, res, next) {
    console.log(new Date().toISOString(), 'url', req.method, req.originalUrl)
    next()
}

// Middleware authenticate
const authenticate = function (req, res, next) {
    console.log("Authenticating:", req.method, req.originalUrl)
    const token = req.header('Authorization').replace('Bearer ', '');
    if (!token)
        return res.status(401).json({ error: 'Authentication failed' });

    let validatedToken = validateJwtToken(token)
    if (validatedToken)
        //res.send(validatedToken)
        next()

    return res.status(403).json({ error: 'Token is not valid' });

}

// Setting up and configure express web server
const router = express.Router()
const app = express()
const port = 3010

// Adding middleware to express
app.use(express.static('wwwroot'))
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }));
app.use(myLogger);

app.post('/travels', [authenticate], (req, res, next) => {
    const token = req.header('Authorization').replace('Bearer ', '');
    let validatedToken = validateJwtToken(token)
    if (validatedToken) {
        let keyword = req.body.destinasjon
        let travels = getTravels (validatedToken.Id, (keyword || ""))
        res.send(travels)
    }
    res.send('Not autorized to search')
});

app.post('/register', (req, res, next) => {
    let email = req.body.email
    let name = req.body.name
    let password = req.body.password
    console.log('register')
    if (email && password && name) {
        let result = registerUser(email, name, password)   
        res.send({ "User registered": result })
    }
});

app.post('/login', (req, res, next) => {
    let email = req.body.email
    let password = req.body.password
    if (email && password) {
        let validated = validateUser(email, password)
        if (validated) {
            let user = getUser(email)
            if (user) {
                let token = createJwtToken(user, {locale: 'nbNO'})
                res.send({ "token": token })
                return
            }
        }
    }
    res.status(400).json({ error: "User or password not correct." });
});

app.post('/getsecureddata', (req, res, next) => {
    res.send('Hello, World!')
});

// Start webserver
app.listen(port, () => {
    console.log(`app running on http://127.0.0.1:${port}`)
});