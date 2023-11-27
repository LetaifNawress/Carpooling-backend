"use strict";
const Eureka = require('eureka-js-client').Eureka;
const client = new Eureka({
    instance: {
        app: 'YOUR_SERVICE_NAME',
        hostName: 'localhost',
        ipAddr: '192.168.43.108',
        port: {
            $: 3002,
            "@enabled": true
        },
        vipAddress: 'YOUR_SERVICE_NAME',
        dataCenterInfo: {
            "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
            name: "MyOwn"
        }
    },
    eureka: {
        host: 'localhost',
        port: 8761 // Port du serveur Eureka
    }
});
client.start((error) => {
    if (error) {
        console.log("Erreur lors de l'enregistrement auprès d'Eureka : " + error);
    }
    else {
        console.log("Microservice enregistré auprès d'Eureka");
    }
});
