// import React, {Component} from 'react';
// import {FaAlignLeft, FaAngleRight} from 'react-icons/fa'
// import {MdViewStream, MdUnfoldMore} from 'react-icons/md'
// import {BsLayers, BsFilter, BsChevronDoubleRight} from 'react-icons/bs'
// import {RiBarChart2Fill} from 'react-icons/ri'
// import {FiCalendar} from 'react-icons/fi'
// import {BiSearch} from 'react-icons/bi'
// import {IoEyeOutline, IoGitMergeOutline, IoFlashOutline, IoNotificationsOutline} from 'react-icons/io5'
// import {HiOutlineUser, HiOutlineUsers, HiOutlineHome} from 'react-icons/hi'
// import {withRouter} from 'react-router'
// import './Css/Navbar.css'
// import {Link} from 'react-router-dom'
//
// class Navbar extends Component {
//
//     constructor(props) {
//         super(props);
//     }
//
//     sign=()=> {
//         this.props.history.push('/signup')
//     }
//
//
//     render() {
//         return (
//             <div className="position">
//
//                 <nav className="nav">
//
//                     <div className="navbar-brand">
//                         <i className="sidebar-icon"><FaAlignLeft/><i className="icon-sle"><BsChevronDoubleRight/></i>
//                             <div className="dropdown">
//                                 <div className="dr-icon-1"><input className="input-dr" type="text"
//                                                                   placeholder="Search"/>
//                                     <i className="drop-icons"><IoFlashOutline/></i>
//                                     <br/>
//                                     <div className="dr-home-2"><i><HiOutlineHome/></i><p className="dr-text">Home</p>
//                                     </div>
//                                     <br/>
//                                     <div className="dr-home-2"><i><IoNotificationsOutline/></i><p
//                                         className="dr-text">Notifications</p></div>
//                                     <br/>
//                                     <div className="dr-border">FAVORITES <i className="dr-str"><FaAngleRight/></i></div>
//                                     <br/>
//                                     <div className="dr-border2">SHARED WITH ME <i className="dr-str"><FaAngleRight/></i>
//                                     </div>
//                                 </div>
//                             </div>
//
//                         </i>
//                         <span className="logo-title">All Tasks</span>
//                     </div>
//                     <ul>
//                         <li><i className="nav-icons" to="/list"><MdViewStream/></i><i className="nav-tit">List</i></li>
//                         <li><i className="nav-icons" to="/board"><RiBarChart2Fill/></i><i className="nav-tit">Board</i>
//                         </li>
//                         <li><i className="nav-icons" to="/calendar"><FiCalendar/></i><i className="nav-tit">Calendar</i>
//                         </li>
//                     </ul>
//
//
//                     <button className="btn ml-auto" style={{color: "#007FE", backgroundColor: "#ccc", fontSize: "13px"}}
//                             onClick={this.sign}>Sign up
//                     </button>
//                     <button className="btn  ml-3"
//                             style={{color: "#007FE", backgroundColor: "#ccc", fontSize: "13px"}}>Login
//                     </button>
//
//
//                 </nav>
//                 <div className="nav2">
//                     <div className="nav-br">
//                         <i className="search-i"><BiSearch/></i><input type="text" placeholder="Filter by task name..."/>
//                     </div>
//                     <div className="nav-br1">
//                         <span className="nav-brr"><i className="icon-b"><BsFilter/></i><a href="#">Filter</a></span>
//                         <span className="nav-brr"><i className="icon-b"><MdUnfoldMore/></i><a
//                             href="#">Sort by</a></span>
//                         <span className="nav-brr"><i className="icon-b"><BsLayers/></i><a href="#">Group by: Status</a></span>
//                         <span className="nav-brr"><i className="icon-b"><IoGitMergeOutline/></i><a href="#">Subtasks</a></span>
//                         <span className="nav-brr"><i className="icon-b"><HiOutlineUser/></i><a href="#">Me</a></span>
//                         <span className="nav-brr"><a href="#"><i className="icon-b"><HiOutlineUsers/></i></a></span>
//                         <span className="nav-brr"><i className="icon-b"><IoEyeOutline/></i><a href="#">Show</a></span>
//                     </div>
//                 </div>
//             </div>
//         );
//     }
// }
//
// export default withRouter(Navbar);