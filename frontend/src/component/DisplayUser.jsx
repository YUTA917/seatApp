import React, { useState, useEffect, useRef } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Button from "react-bootstrap/Button";

import axios from "axios";

export default function DisplayUser(props) {
	const { setUserId, reload } = props;
	const inputEl = useRef(null);
	const [users, setUsers] = useState([]);

	const userList = users;

	useEffect(() => {
		axios
			.get("http://localhost:8080/users")
			.then((res) => {
				setUsers(res.data);
			})
			.catch((err) => {
				console.log("err:", err);
			});
	}, [reload]);

	console.log(users);

	return (
		<div className="nameArea">
			{users.map((ele) => {
				return (
					<>
						<Button
							className="userName"
							variant="primary"
							size="sg"
							key={ele.id}
							id={ele.id}
							onClick={(ele) => {
								setUserId(ele.target.id);
							}}
						>
							{ele.name}
						</Button>
					</>
				);
			})}
		</div>
	);
}
