<template>
    <b-container>
        <b-row class="mt-1 d-flex justify-content-end">
            <b-button variant="danger" size="sm" @click="clearAll()">
                    Reset All
            </b-button>
        </b-row>
        <h1 class="pt-4"> Event Registration </h1>
         <b-row class="mt-3">
            <b-col id="registration-overview" class="mr-1 p-2">
                <h3> Registrations </h3>
                <table>
                    <tr>
                        <th> </th>
                        <th> Name </th>
                        <th> Events </th>
                        <th> Credit Card </th>
                        <th> Amount </th>
                        
                    </tr>
                    <tr v-for="(person, index) in persons" v-bind:key="person.index" :key="index">
                        <td>
                            <b-button variant="danger" size="sm" @click="deletePerson(person.name)"> X </b-button>
                        </td>
                        <td>
                            {{person.name}}
                        </td>
                        <td id="event-names" v-for="(event, index) in person.eventsAttended" v-bind:key="person.index">
                                {{event.name}}
                        </td>
                        <td v-for="(event, index) in person.eventsAttended" v-bind:key="person.index">
                            {{event.creditCard != null ? event.creditCard.accountNumber : ""}}
                        </td>
                        <td v-for="(event, index) in person.eventsAttended" v-bind:key="person.index">
                            {{event.creditCard != null ? event.creditCard.amount : ""}}
                        </td>
                    </tr>
                </table>
            </b-col>
        </b-row>
        <b-row class="mt-3">
            <b-col id="section" class="ml-1 p-2">
                <h3> Events </h3>
                <table>
                <tr>
                    <th> </th>
                    <th> Name </th>
                    <th> Description </th>
                    <th> Date </th>
                    <th> Start Time </th>
                    <th> End Time</th>
                </tr>
                <tr v-for="(event, index) in events" v-bind:key="event.index" :key="index">
                    <td>
                        <b-button variant="danger" size="sm" @click="deleteEvent(event.name)"> X </b-button>
                    </td> 
                    <td> {{ event.name }} </td>
                    <td> {{ event.description }} </td>
                    <td> {{ event.date }} </td>
                    <td> {{ event.startTime }} </td>
                    <td> {{ event.endTime }} </td> 
                </tr>
                    
                </table>
            </b-col>
        </b-row>
        <hr>
        <b-row class="mt-3">
            <b-col class="p-2">
                <h3> Create New Person </h3>
                <div>
                    <input type="text" v-model="newPerson" placeholder="Person Name">
                    <select v-model="personType">
                        <option value="Person"> Person </option>
                        <option value="Organizer"> Organizer </option>
                    </select>
                    <b-button variant="success" size="sm" v-bind:disabled="!newPerson" @click="createPerson(personType, newPerson)"> Create </b-button>
                </div>
                <p>
                    <span v-if="errorPerson" style="color:red"> Error: {{errorPerson}} </span>
                </p>
                <h3> Create New Event </h3>
                <div>
                    <input type="text" v-model="newEvent.name" placeholder="Event Name">
                    <input type="text" v-model="newEvent.description" placeholder="Description">
                    <input type="date" v-model="newEvent.date">
                    <input type="time" v-model="newEvent.startTime"> 
                    <input type="time" v-model="newEvent.endTime">
                    <b-button variant="success" size="sm" v-bind:disabled="!newEvent.name" @click="createEvent(newEvent)"> Create </b-button>
                </div>
                 <p>
                    <span v-if="errorEvent" style="color:red"> Error: {{errorEvent}} </span>
                </p>
            </b-col>
        </b-row>        
        <b-row class="mt-3">
            <b-col class="mr-1 p-2">
                    <h3> Assign Organizers </h3>
                    <label>Organizer:
                        <select v-model="selectedOrganizer">
                            <option disabled value="">Please select one...</option>
                            <option v-for="(organizer, index) in organizers" v-bind:key="organizer.index" :key="index">
                                {{organizer.name}}
                            </option>
                        </select>
                    </label>
                    <label>Event:
                        <select v-model="selectedEventA">
                            <option disabled value="">Please select one...</option>
                            <option v-for="(event, index) in events" v-bind:key="event.index" :key="index">
                                {{event.name}}
                            </option>
                        </select>
                    </label>
                    <div>
                        <b-button variant="success" size="sm" v-bind:disabled="!selectedOrganizer || !selectedEventA" @click="organizeEvent(selectedOrganizer, selectedEventA)">
                            Assign
                        </b-button>
                    </div>
                    <span v-if="errorRegistration" style="color:red">Error: {{errorRegistration}}</span> 
            </b-col>        
            <b-col class="ml-1 p-2">
                    <h3> Register Persons </h3>
                    <label>Person:
                        <select v-model="selectedPersonR">
                            <option disabled value="">Please select one...</option>
                            <option v-for="(person, index) in persons" v-bind:key="person.index" :key="index">
                                {{person.name}}
                            </option>
                        </select>
                    </label>
                    <label>Event:
                        <select v-model="selectedEventR">
                            <option disabled value="">Please select one...</option>
                            <option v-for="(event, index) in events" v-bind:key="event.index" :key="index">
                                {{event.name}}
                            </option>
                        </select>
                    </label>
                    <div>
                        <b-button variant="success" size="sm" v-bind:disabled="!selectedPersonR || !selectedEventR" @click="registerEvent(selectedPersonR, selectedEventR)">
                            Register
                        </b-button>
                    </div>
                    <span v-if="errorRegistration" style="color:red">Error: {{errorRegistration}}</span>  
            </b-col>
        </b-row>
        <b-row class="mt-3">
            <b-col class="p-2">
                <h3> Record Payment </h3>
                <label>Person:
                    <select v-model="selectedPersonCC">
                        <option disabled value="">Please select one...</option>
                        <option v-for="(person, index) in persons" v-bind:key="person.index" :key="index">
                            {{person.name}}
                        </option>
                    </select>
                </label>
                <label> Event:
                    <select v-model="selectedEventCC">
                        <option disabled value="">Please select one...</option>
                        <option v-for="(event, index) in events" v-bind:key="event.index" :key="index">
                            {{event.name}}
                        </option>
                    </select>
                </label>
                <label> Account Number:
                    <input type="text" v-model="accountNumber" placeholder="1234-1234-1234-1234">
                </label>
                <label> Amount:
                    <input type="float" v-model="amount" placeholder="000.00">
                </label>
                <b-button variant="success" size="sm" v-bin:disabled="!selectedPersonCC || !selectedEventCC || !accountNumber || !amount" @click="recordPayment(selectedPersonCC, selectedEventCC)">
                    Record
                </b-button>
                 <span v-if="errorPayment" style="color:red">Error: {{errorPayment}}</span>
            </b-col>
        </b-row>
        
    </b-container>
    
</template>
<script src="./registration.js">

</script>
<style>
    #registration-overview {
        border: 6px solid;
        border-radius: 20px;
    }
    #section {
        border: 2px solid;
        border-radius: 20px;
    }
    #event-names {
        display: block;
    }
    table {
        width: 100%;
        text-align: center;
    }
    th {
        border-bottom: 1px solid;
    }
    #registration {
        height: 500px;
    }
    h3 {
        font-size: 20px;
        font-weight: bold;
        text-decoration: underline;
    }

</style>
