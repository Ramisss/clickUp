// import React, {Component} from 'react';
// import {DragDropContext, Draggable, Droppable} from "react-beautiful-dnd";
// import {BsThreeDots} from 'react-icons/bs';
// import {FaCalendarCheck, FaRegHourglass, FaStarOfDavid} from "react-icons/fa";
// import {CgFlagAlt} from 'react-icons/cg';
// import './Css/Board.css'
//
// class Drag extends Component {
//     state = {
//         statuses: [
//             {
//                 id: 1,
//                 name: {},
//                 countTask: 10,
//                 tasks: [
//                     {
//                         id: 1,
//                         name: "Task header first",
//                         status_id: 1
//                     },
//                     {
//                         id: 5,
//                         title: "Task headeri fifth",
//                         status_id: 1
//                     },
//                     {
//                         id: 6,
//                         title: "Task headeri six",
//                         status_id: 1
//                     },
//                 ]
//             },
//             {
//                 id: 2,
//                 name: "PENDING",
//                 countTask: 10,
//                 tasks: [
//
//                     {
//                         id: 2,
//                         title: "Task headeri second",
//                         status_id: 2
//                     },
//                 ]
//             },
//             {
//                 id: 3,
//                 name: "CLOSED",
//                 countTask: 10,
//                 tasks: [
//                     {
//                         id: 3,
//                         title: "Task header third",
//                         status_id: 3
//                     },
//                 ]
//             },
//             {
//                 id: 4,
//                 name: "TEST",
//                 countTask: 10,
//                 tasks: [
//                     {
//                         id: 4,
//                         title: "Task headeri fourth",
//                         status_id: 4
//                     },
//                 ]
//             }
//         ],
//         count: 0
//     }
//
//
//
//
//     handleOnDragEnd = (item) => {
//         if (item.destination) {
//             let statuses = this.state.statuses;
//             let currentObj = '';
//             for (let i = 0; i < statuses.length; i++) {
//                 if (statuses[i].id == item.source.droppableId) {
//                     currentObj = statuses[i].tasks[item.source.index];
//                     statuses[i].tasks.splice(item.source.index, 1);
//                     break;
//                 }
//             }
//             for (let i = 0; i < statuses.length; i++) {
//                 if (statuses[i].id == item.destination.droppableId) {
//                     statuses[i].tasks.splice(item.destination.index, 0, currentObj)
//                     break;
//                 }
//             }
//             console.log(this.state)
//             // this.state.count=5
//             // this.setState({count: 5})
//         }
//
//
//         // let newStatus = this.state.statuses.filter(i => i.id == item.destination.droppableId)[0];
//         // let oldStatus = this.state.statuses.filter(i => i.id == item.source.droppableId)[0];
//         // currentObj = oldStatus.tasks[item.source.index];
//         // newStatus.tasks.splice(item.destination.index, 0, currentObj)
//         // oldStatus.tasks.splice(item.source.index, 1);
//
//
//     }
//
//     render() {
//         const {statuses, count} = this.state;
//         return (
//             <div className="board">
//                 <div className="container">
//                     <DragDropContext onDragEnd={this.handleOnDragEnd}>
//                         {statuses.map((obj, index) =>
//                             <Droppable key={obj.id} droppableId={"" + obj.id}>
//                                 {(provided) => (
//                                     <section className="section"
//                                              {...provided.droppableProps}
//                                              ref={provided.innerRef}>
//                                         <div className="column">
//                                             <div className="card">
//                                                 <div className="card-header">
//                                                     <span className="card-text">{obj.name}</span>
//                                                     <span className="card-sif">{count}</span>
//                                                     <span className="card-butt"><i
//                                                         className="card-butt-icon"><BsThreeDots/></i>+</span>
//                                                 </div>
//                                             </div>
//                                             {obj.tasks.map((item, index) => {
//                                                 return (
//                                                     <Draggable key={Math.random()}
//                                                                draggableId={"" + item.id + ""}
//                                                                index={index}>
//                                                         {(provided) => (
//                                                             <div className="card-body"
//                                                                  ref={provided.innerRef}
//                                                                  {...provided.draggableProps}
//                                                                  {...provided.dragHandleProps}>
//                                                                 <div className="card-header-body">
//                                                                     <div className="card-body-header-text">Shared with
//                                                                         me > Mfaktor.uz > task list
//                                                                     </div>
//                                                                     <div className="card-body-text">{item.title}
//                                                                     </div>
//                                                                 </div>
//                                                                 <div className="card-footer" id="div">
//                                                                     <span
//                                                                         className="card-foo-icon"><i><FaCalendarCheck/></i></span>
//                                                                     <span className="card-foo-icon"><CgFlagAlt/></span>
//                                                                     <span
//                                                                         className="card-foo-icon"><FaRegHourglass/></span>
//                                                                     <span
//                                                                         className="card-foo-icon"><FaStarOfDavid/></span>
//                                                                 </div>
//                                                             </div>
//                                                         )}
//                                                     </Draggable>
//                                                 );
//                                             })}
//                                             {provided.placeholder}
//                                         </div>
//                                     </section>
//
//                                 )}
//                             </Droppable>
//                         )}
//                     </DragDropContext>
//                 </div>
//             </div>
//         );
//     }
// }
//
// Drag.propTypes = {};
//
// export default Drag;