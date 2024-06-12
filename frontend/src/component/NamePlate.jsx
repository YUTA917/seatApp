import React from "react";
import "./NamePlate.css";

function NamePlate(props) {
	const { name } = props;
	return <div className="namePlate">{name}</div>;
}

export default NamePlate;
