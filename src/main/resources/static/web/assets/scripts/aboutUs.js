let { createApp } = Vue;
createApp({
    data() {
        return {
            
        }
    },
    created() {
        
    },
    methods: {
        sessionLogOut() {
            axios.post("/api/logout")
            .then(res => {
                window.location.href = "/web/index.html"
                console.log("signedOUT")
            }).catch(err => {console.log(err)})
        }
    }
}).mount("#app")