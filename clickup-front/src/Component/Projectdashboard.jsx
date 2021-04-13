import React, {Component} from "react";
import {TOKEN} from "../utils/constants";
import axios from 'axios';
import api from '../utils/api'
import "./Css/Projectdashboard.css"
import Draggable from "react-draggable";
import Card from "./Card";


class Projectdashboard extends Component {

    constructor(props) {
        super(props)

        this.state = {
            projects: [
                {
                    projectId: '',
                    name: ''
                }
            ]
        }
    }

    componentDidMount() {
        if (!localStorage.getItem(TOKEN)) {
            this.props.history.push('/home')
            // console.log(api)
        } else {
            this.getProjects()
        }
    }

    getProjects = () => {
        console.log(localStorage.getItem(TOKEN))
        axios.get(api.projectUrl + '/findProjects', {
            headers: {
                // 'Content-Type': 'application/json',
                'Authorization': localStorage.getItem(TOKEN)
            }
        })
            .then(res => {
                // console.log(res.data)
                // this.setState(res.data.object)

                this.setState({projects: res.data.object})
                console.log(this.setState({projects: res.data.object}))

            }).catch(error => {
            console.log(error.data)
        })
    }

    selectproject = (e) => {

    }


    render() {
        const {projects} = this.state;
        return (
            <div style={{  backgroundColor:"#eee"}}>
                <h1 style={{fontFamily: "Tahoma", fontWeight: "600",textAlign:"center"}}>
                    Projects
                </h1>

                {/*<Draggable> */}
                    {projects.map((item, i) => {
                    return <a href="#" onClick={this.selectproject} style={{color: "#592424",textAlign:"center"}}>
                        <span>{item.name}</span>
                    </a>
                })}
                {/*</Draggable>*/}

                {console.log(projects)}

            </div>
        )
    }
}



export default Projectdashboard;