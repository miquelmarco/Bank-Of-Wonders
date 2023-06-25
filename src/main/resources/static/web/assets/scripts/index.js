let {createApp} = Vue

createApp({
    data(){
        return {
            email:'',
            password:'',
        }
    },
    created(){

    },
    methods:{
        sessionLogIn() {
            axios.post("/api/login", `email=${this.email}&password=${this.password}`,
            {headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(res => {
                console.log("signed in")
                window.location.href = "/web/pages/accounts.html"
            }).catch(err => {console.err(err),
            alert("Email or password incorrect!")})
        }
    }
}).mount("#app")

// axios.post('/api/login',"email=melba@mindhub.com&password=melba",{headers:{'content-type':'application/x-www-form-urlencoded'}}).then(response => console.log('signed in!!!'))