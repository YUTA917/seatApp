import React, { useState, useRef } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import axios from "axios";

function DeleteUserButton(props) {
	const { reload, setReload } = props;
	const [show, setShow] = useState(false);
	const inputEl = useRef(null);

	const handleClose = () => setShow(false);
	const handleShow = () => setShow(true);

	const deleteUser = (name) => {
		axios
			.delete(`http://localhost:8080/users/${name}`)
			.then((res) => {
				console.log(res.data);
				setReload(!reload);
			})
			.catch((err) => {
				console.log("err:", err);
			});
	};

	return (
		<>
			<div className="funcButton">
				<Button
					className="operationButton"
					variant="primary"
					size="lg"
					onClick={handleShow}
				>
					ユーザー削除
				</Button>
			</div>
			<Modal show={show} onHide={handleClose}>
				<Modal.Header closeButton>
					<Modal.Title>Modal heading</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<Form.Label htmlFor="inputPassword5">
						ユーザー名を入力してください
					</Form.Label>
					<Form.Control ref={inputEl} type="text" id="inputName" />
				</Modal.Body>
				<Modal.Footer>
					<Button variant="secondary" onClick={handleClose}>
						Close
					</Button>
					<Button
						variant="primary"
						onClick={() => {
							deleteUser(inputEl.current.value);
							handleClose();
						}}
					>
						保存
					</Button>
				</Modal.Footer>
			</Modal>
		</>
	);
}

export default DeleteUserButton;
