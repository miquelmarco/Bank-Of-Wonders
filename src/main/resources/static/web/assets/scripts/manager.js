let { createApp } = Vue;

createApp({
    data() {
        return {
            clients: [],
            todoJSON: "",
            newClient: { firstName: '', lastName: '', email: ''}
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get(`http://localhost:8080/clients`)
                .then(res => {
                    this.todoJSON = res.data
                    this.clients = res.data._embedded.clients
                    console.log(this.todoJSON)
                }).catch(err => console.error(err))
        },
        addClient() {
            if (this.newClient.firstName !== "" && this.newClient.lastName !== "" && this.newClient.email !== "") {
                this.postClient();
            } else {
                alert("All fields are necessary");
            }
        },
        postClient() {
            axios.post(`http://localhost:8080/clients`,{firstName: this.newClient.firstName, lastName: this.newClient.lastName, email: this.newClient.email})
                .then(res => {
                    this.loadData()
                    this.eraseFields()
                }).catch(err => console.log(err))
        },
        eraseFields(){
            this.newClient.firstName = '',
            this.newClient.lastName = '',
            this.newClient.email = ''
        },
        deleteClient(id){
            axios.delete(id)
            .then(res => {
                this.loadData()
            }).catch(err => console.log(err))
        },
    }
}).mount("#app")