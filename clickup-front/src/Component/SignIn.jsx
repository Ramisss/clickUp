import React, {Component} from "react";
import axios from 'axios'
import {TOKEN} from "../utils/constants";

class SignIn extends Component {
    constructor(props) {
        super(props);

        this.state = {
            email: '',
            password: ''

        }
    }

    login = () => {
        axios.post("http://localhost:8090/api/auth/login", {email: this.state.email, password: this.state.password})
            .then(res => {
                // console.log("res.payload",res)
                // console.log(res.data)
                // const raw=localStorage.setItem('token', JSON.stringify(res.data))
                localStorage.setItem(TOKEN, 'Bearer ' + res.data)

                console.log(localStorage.getItem('clickToken'))
                this.props.history.push('/dashboard')
                // localStorage.setItem('tokencha',res.data.toString())
                // localStorage.getItem(res.data)
                // this.props.history.push('/dashboard')
            }).catch(err => {
            alert(err.response.data);
            this.props.history.push('/signup');
        })
    }


    //EXAMPLE BEGIN
    axios = () => ({
        method: 'GET',
        url: 'http://localhost:8085/api/auth/login',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    }).then(resp => {
        console.log(resp.data);
    }).catch(err => {
        // Handle Error Here
        console.log(err);
    });

    // EXAMPLE FINISH


    render() {
        return (
            <div className="container">
                <div className="row pt-5">
                    <div className="card col-md-6 offset-md-3 offset-md-3 ">

                        <div className="card-body ">
                            <form>
                                <div className="form">
                                    <label>Email: </label>
                                    <input placeholder="email" name="email" type="email" className="form-control"
                                           onChange={(event) => this.setState({email: event.target.value})}
                                           required/>
                                </div>

                                <div className="form-group">
                                    <label>Password: </label>
                                    <input placeholder="Password" name="password" type="password" required
                                           className="form-control "
                                           onChange={(event) => this.setState({password: event.target.value})}
                                    />
                                </div>
                                {console.log(this.state)}
                                <button type="button" className="btn btn-success" onClick={this.login}>
                                    Sign In
                                </button>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default SignIn;