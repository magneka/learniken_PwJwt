/* ------------------------------------------------------------
   Ta en base 64 encoded string
   Konverter til en string av hexadesimale
   ------------------------------------------------------------
*/
exports.stringToHexString = stringToHexString = (_hashedPwd) => {
   
    // Resultat
    let hexResult = ""

    // Base64encode strengen inn i en array av bytes
    const byteArray = Buffer.from(_hashedPwd, 'base64')

    // Loop gjennom byte arrayen og generer 0 padded hex verdi
    byteArray.forEach(byte => {
        hexResult += byte.toString(16).toUpperCase().padStart(2, '0')
    });

    return hexResult
}

/* ------------------------------------------------------------
   Ta en string bestående av hexverdier (to og to bokstaver)
   Konverter tilbake til base64 decoded streng som kan lagres 
   i base som f.eks. hashed passord   
   ------------------------------------------------------------
*/
exports.hexStringToString = hexStringToString = (inHexString) => {

    let byteArr = []
    for (let i = 0; i < inHexString.length; i = i + 2) {
        let result = inHexString.substring(i, i + 2);
        //console.log(i, result)
        //console.log(i, Number("0x" + result));
        byteArr.push(Number("0x" + result))
    }
    const buffer2 = Buffer.from(byteArr);
    let decodedResult = buffer2.toString("base64");
    return decodedResult
}

/* ------------------------------------------------------------
   Hent data fra hex string fra til posisjoner   
   ------------------------------------------------------------
*/
exports.getBytes = getBytes = (hexstring, from, to) => {
    return hexstring.substring(from * 2 - 2, to * 2)
}