const express = require('express')
const app = express()
const port = process.env.MOCK_SERVER_PORT || 5000
let currentId = 0

var bodyParser = require('body-parser');
app.use(bodyParser.json()); // support json encoded bodies
app.use(bodyParser.urlencoded({ extended: true })); // support encoded bodies

const contacts = [
  {
    id: currentId++,
    name: "Vineet",
    phone: "82955127342",
    email: "hello123@gmail.com",
    createdAt: new Date(),
    updatedAt: new Date()
  },
  {
    id: currentId++,
    name: "Raghav",
    phone: "82955127342",
    email: "dmg@gmail.com",
    createdAt: new Date(),
    updatedAt: new Date()
  },
  {
    id: currentId++,
    name: "Ashish",
    phone: "82955127342",
    email: "ashsih@xyz.com",
    createdAt: new Date(),
    updatedAt: new Date()
  }
]

app.get('/contacts', (req, res) => {
  res.send(contacts)
})

app.post('/addContact', (req, res) => {
  let cont = {}
  cont['id'] = currentId++
  cont['name'] = req.body.name
  cont['email'] = req.body.email
  cont['phone'] = req.body.phone
  cont['createdAt'] = new Date()
  cont['updatedAt'] = new Date()
  contacts.push(cont)
  res.send(cont)
})


app.listen(port, () => console.log(`Listening on port ${port}`))
