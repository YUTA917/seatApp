import { useState } from "react";
import LeftArea from "./component/LeftArea";
import RightArea from "./component/RightArea";
import "./App.css";

function App() {
	const [userId, setUserId] = useState(undefined);

	return (
		<div className="All">
			<LeftArea setUserId={setUserId} className="LeftArea"></LeftArea>
			<div style={{ width: "5%" }}></div>
			<RightArea className="RightArea" userId={userId}></RightArea>
		</div>
	);
}

export default App;
