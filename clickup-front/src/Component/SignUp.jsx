import React, {Component} from 'react';
import RegUserService from "../Service/RegUserService";


class SignUp extends Component {
    constructor(props) {
        super(props);

        this.state = {
            id: this.props.match.params.id,
            firstName: '',
            lastName: '',
            email: '',
            password: '',
            phoneNumber: ''

        }
        this.handleFirstName = this.handleFirstName.bind(this);
        this.handleLastName = this.handleLastName.bind(this);
        this.handleEmail = this.handleEmail.bind(this);
        this.handlePassword = this.handlePassword.bind(this);
        this.handlePhoneNumber = this.handlePhoneNumber.bind(this);
        this.saveUser = this.saveUser.bind(this);

    }


    handleFirstName = (e) => {
        this.setState({firstName: e.target.value})
    }

    handleLastName = (e) => {
        this.setState({lastName: e.target.value})
        console.log(this.state.lastName)
    }

    handleEmail = (e) => {
        this.setState({email: e.target.value})
        console.log(this.state.email)

    }

    handlePassword = (e) => {
        this.setState({password: e.target.value})
    }

    handlePhoneNumber = (e) => {
        this.setState({phoneNumber: e.target.value})
    }

    saveUser = (e) => {
        e.preventDefault();
        let user = {
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            email: this.state.email,
            password: this.state.password,
            phoneNumber: this.state.phoneNumber
        }
        // console.log('user =>' + JSON.stringify(user));
        RegUserService.addUser(user).then(res => {
            // localStorage.setItem('clickToken', 'Bearer '+JSON.stringify(res.data))
            this.props.history.push('/dashboard')
            // alert("go to login");
            console.log(res);
        })
    }

    cancel=(e)=>{
        this.props.history.push('/home')

    }

    // componentDidMount(e) {
    //     e.preventDefault();
    //
    // }




    render() {
        return (
            <div className="col-md-12">

                <div className="row pt-4">
                    <div className="card col-md-6 offset-md-3 offset-md-3 ">

                        <div className="card-body ">
                            <form>
                                <div className="form-group">
                                    <label>First name: </label>
                                    <input placeholder="First name" name="firstName" required={true}
                                           className="form-control"
                                           value={this.state.firstName} onChange={this.handleFirstName}/>
                                </div>

                                <div className="form-group">
                                    <label>Last name: </label>
                                    <input placeholder="Last name" name="lastName" required className="form-control"
                                           value={this.state.lastName} onChange={this.handleLastName}/>
                                </div>

                                <div className="form -group">
                                    <label>Email: </label>
                                    <input placeholder="email"  type="email" name="email" required className="form-control"
                                           value={this.state.email} onChange={this.handleEmail}/>

                                </div>

                                <div className="form-group">
                                    <label>Password: </label>
                                    <input placeholder="Password" name="password" required className="form-control"
                                           value={this.state.password} onChange={this.handlePassword}/>
                                </div>

                                <div className="form-group">
                                    <label>Phone number: </label>
                                    <input placeholder="phone number" name="phoneNumber"
                                           className="form-control"
                                           value={this.state.phoneNumber} onChange={this.handlePhoneNumber}/>

                                </div>

                                <button className="btn btn-success" onClick={this.saveUser}>
                                    Sign In
                                </button>

                                <button className="btn btn-danger"onClick={this.cancel} >
                                    Cancel
                                </button>

                            </form>
                        </div>
                    </div>
                </div>

            </div>
        )
    }
}

export default SignUp;