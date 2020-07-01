
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create ({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

  export default {
      name: 'eventregistration',
      data() {
          return {
            persons: [],
            organizers: [],
            events: [],
            newPerson: '',
            personType: 'Person',
            newEvent: {
            name: '',
            description: '',
            date: '2020-01-01',
            startTime: '09:00',
            endTime: '21:00',
            },
            selectedPersonR: '',
            selectedEventR: '',
            errorPerson: '',
            errorEvent: '',
            errorRegistration: '',
            response: [],
          }
      },
      created: function() {
          AXIOS.get('/persons')
          .then(response => {
              this.persons = response.data
              console.log(this.persons)
          })
          .catch(e => {
              this.errorPerson = e
          });
          AXIOS.get('/organizers')
          .then(response => {
              this.organizers = response.data
          })
          .catch(e => {
              this.errorPerson = e
          });
          AXIOS.get('/events')
          .then(response => {
              this.events = response.data
          })
          .catch(e => {
              this.errorEvent = e
          })
      },
      methods: {
        reload: function() {
            AXIOS.get('/persons')
            .then(response => {
                this.persons = response.data
            })
            .catch(e => {
                this.errorPerson = e
            });
            AXIOS.get('/events')
            .then(response => {
                this.events = response.data
            })
            .catch(e => {
                this.errorEvent = e
            })
        },
        createPerson: function(personType, personName) {
            if(personType == "Person") {
                AXIOS.post('/persons/'.concat(personName), {}, {})
                .then(response => {
                    this.persons.push(response.data)
                    this.errorPerson = ''
                    this.newPerson = ''
                })
                .catch(e => {
                    e = e.response.data.message ? e.response.data.message : e
                    this.newPerson = ''
                    this.errorPerson = e
                    console.log(e);
                });
            }
            if(personType == "Organizer") {
                AXIOS.post('/organizers/'.concat(personName), {}, {})
                .then(response => {
                    this.persons.push(response.data)
                    this.organizers.push(response.data)
                    this.errorPerson = ''
                    this.newPerson = ''
                })
                .catch(e => {
                    e = e.response.data.message ? e.response.data.message : e
                    this.newPerson = ''
                    this.errorPerson = e
                });
            }
        },
        deletePerson: function(personName) {
            AXIOS.delete('/persons/'.concat(personName))
            .then(
                this.reload
            )
            .catch(e => {
                var errorMessage = e.message
                this.errorPerson = errorMessage
            })
        },
        createEvent: function(newEvent) {
            AXIOS.post('/events/'.concat(newEvent.name), {}, {params: newEvent})
            .then(response => {
                this.events.push(response.data)
                this.errorEvent = ''
                this.newEvent.name = ''
                this.newEvent.description = ''
            })
            .catch(e => {
                e = e.response.data.message ? e.response.data.message : e
                this.errorEvent = e
            }) 
        },
        deleteEvent: function(event) {
            AXIOS.delete('/events/'.concat(event))
            .then(
                this.reload
            )
            .catch(e => {
                var errorMessage = e.message
                this.errorEvent = errorMessage
            })
        },
        registerEvent: function (personName, eventName) {
            let person = this.persons.find(x => x.name === personName);
            let event = this.events.find(x => x.name === eventName);
            let params = {
              person: person.name,
              event: event.name
            };
            AXIOS.post('/register', {}, {params: params})
            .then(response => {
              person.eventsAttended = response.data.person.eventsAttended;
              this.selectedPersonR = '';
              this.selectedEventR = '';
              this.errorRegistration = '';
            })
            .catch(e => {
              e = e.response.data.message ? e.response.data.message : e;
              this.errorRegistration = e;
            });
        },
        getRegistrations: function (personName) {
            AXIOS.get('/events/person/'.concat(personName))
            .then(response => {
                if (!response.data || response.data.length <= 0) return;
                let indexPart = this.persons.map(x => x.name).indexOf(personName);
                this.persons[indexPart].eventsAttended = [];
                response.data.forEach(event => {
                    this.persons[indexPart].eventsAttended.push(event);
                });
            })
            .catch(e => {
              e = e.response.data.message ? e.response.data.message : e;
            });
        },
      }
  }