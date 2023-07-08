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
                    if (res.status == 200) {
                        Swal.fire({
                            position: 'top-center',
                            title: 'Bye bye!',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        setTimeout(() => {
                            window.location.href = "/web/index.html";
                        }, 1800)
                    }
                    // window.location.href = "/web/index.html"
                    console.log(res)
                }).catch(err => { console.log(err) })
        }
    }
}).mount("#app")