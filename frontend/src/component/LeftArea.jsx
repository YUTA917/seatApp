import React, { useState, useEffect } from "react";
import DisplayUser from "./DisplayUser";
import AddUserButton from "./AddUserButton";
import DeleteUserButton from "./DeleteUserButton";
import "./LeftArea.css";

export default function LeftArea(props) {
	const { setUserId } = props;
	const [reload, setReload] = useState(false);

	return (
		<div>
			<div className="smallTitle">
				<h3>ユーザー選択してね</h3>
			</div>
			<DisplayUser reload={reload} setUserId={setUserId} />
			<div className="buttonArea">
				<AddUserButton
					className="funcButton"
					reload={reload}
					setReload={setReload}
				/>
				<DeleteUserButton
					className="funcButton"
					reload={reload}
					setReload={setReload}
				/>
			</div>
		</div>
	);
}
