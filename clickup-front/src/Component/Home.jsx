import React, {Component} from "react";
import "./Css/Home.css"


class Home extends Component{

    constructor(props){
      super(props);


    }


render() {return (
    <div className="container-lg-12" >
        <h1 style={{fontSize:"38px",fontStyle:"italic",padding:"30px",margin:"auto"}}> Welcome to home page!!</h1>
    </div>
)
}


}


export default Home;