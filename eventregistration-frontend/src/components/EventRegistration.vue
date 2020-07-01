<template>
    <div id="eventregistration">
        <h1> Event Registration </h1>
        <div id="section">
            <table>
                <tr v-for="person in persons">
                    <td> {{ person.name }}</td>
                    <td> Event to Attend </td>
                </tr>
                <tr>
                    <td>
                        <input type="text" v-model="newPerson" placeholder="Person Name">
                    </td>
                    <td>
                        <b-button v-bind:disabled="!newPerson" @click="createPerson(newPerson)"> Create </b-button>
                    </td>
                </tr>
            </table>
        </div>
        <p>
            <span v-if="errorPerson" style="color:red"> Error: {{errorPerson}} </span>
        </p>
    </div>
    
</template>
<script src="./registration.js">

</script>
<style>
    #eventregistration {
    }
    #section {
        display: flex;
        justify-content: center;
    }
</style>
