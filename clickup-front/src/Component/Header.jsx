import React, {Component} from "react";
import {BrowserRouter as Router} from "react-router-dom";
import "./Css/Head.css"




class Header extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div id="mySidenav" className="sidenav">
                <a href="/home" id="about">Home</a>
                <a href="/signUp" id="blog">Sign up</a>
                <a href="/login" id="projects">Sign in</a>
                <a href="#" id="contact">About</a>
            </div>

        )
    }

}


export default Header;