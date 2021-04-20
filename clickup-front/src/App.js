import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import React from "react";
import Drag from "./Component/Drag";
import Navbar from "./Component/Navbar";
import Header from "./Component/Header";
import SignIn from "./Component/SignIn";
import SignUp from "./Component/SignUp";
import Delete from "./Component/Delete"
import Projectdashboard from "./Component/Projectdashboard";
import DragComponent from "./Component/DragComponent";
import Home from "./Component/Home";

function App() {
    return (
        <div>
            <Router>
                <div>
                    {/*<Navbar/>*/}
                    <Header/>
                    <Switch>
                        <Route path="" component={Home}></Route>
                        <Route path="/signup" component={SignUp}></Route>
                        <Route path="/login" component={SignIn}></Route>
                        <Route path="/dashboard" component={Projectdashboard}></Route>
                        {/*<Route path="/drag" component={DragComponent}></Route>*/}
                        {/*<Route path="/dr" component={Drag}></Route>*/}
                        {/*<Car/>*/}
                        {console.log("Salom")}
                    </Switch>
                </div>
            </Router>
        </div>
    );
}


export default App;
