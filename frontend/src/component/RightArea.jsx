import React, { useState, useEffect } from "react";
import ReactDOMServer from "react-dom/server";
import { CRS, LatLng, LatLngBounds } from "leaflet";
import L from "leaflet";
import { MapContainer, ImageOverlay, Marker, useMapEvent } from "react-leaflet";
import "leaflet/dist/leaflet.css"; // 追加
import bgImage from "../../public/image/座席.jpeg";
import NamePlate from "./NamePlate";
import axios from "axios";
// import { ImageOverlay } from "https://cdn.esm.sh/react-leaflet/ImageOverlay";

// import "./RightArea.css";

export default function RightArea(props) {
	const { userId } = props;
	const [seats, setSeats] = useState([]);
	const [reload, setReload] = useState(false);

	useEffect(() => {
		axios
			.get("http://localhost:8080/seats")
			.then((res) => {
				debugger;
				setSeats(res.data);
			})
			.catch((err) => {
				console.log("err:", err);
			});
	}, [reload]);

	// console.log(seats);
	//シートの画像
	const seatImage = {
		url: bgImage,
		width: 677,
		height: 508,
	};

	const position = [130, 330];

	function NameIcon(name) {
		return L.divIcon({
			html: ReactDOMServer.renderToString(<NamePlate name={name} />),
			iconSize: [80, 50],
		});
	}

	function postName(id, name) {
		let body;
		console.log(id, name);
		if (name === null) {
			body = { seatId: id, userId, filled: true };
		} else {
			body = { seatId: id, userId: null, filled: true };
		}
		console.log(body);
		axios
			.put("http://localhost:8080/seats", body)
			.then((res) => {
				console.log(res.data);
				setReload(!reload);
			})
			.catch((err) => {
				console.log("err:", err);
			});
	}

	function MyComponent() {
		const map = useMapEvent("click", (e) => {
			let lat = e.latlng.lat;
			let lng = e.latlng.lng;
			console.log(lat, lng);
		});
		return null;
	}

	return (
		<>
			{/* <img src={"../../public/image/座席.png"}></img> */}
			<MapContainer
				center={new LatLng(seatImage.height / 2, seatImage.width / 2)}
				zoom={0}
				style={{ width: 800, height: 600 }}
				crs={CRS.Simple}
				className="map"
				zoomControl={false}
			>
				<ImageOverlay
					url={bgImage}
					bounds={
						new LatLngBounds([
							[0, 0],
							[seatImage.height, seatImage.width],
						])
					}
				/>
				<MyComponent />
				{console.log(seats)}
				{seats !== undefined &&
					seats.map((ele) => {
						// const marker = L.marker([ele.x, ele.y]);
						// map.addLayer(marker);
						console.log(ele.id, ele.name);
						return (
							<Marker
								key={ele.id}
								position={[ele.x, ele.y]}
								icon={NameIcon(ele.name)}
								eventHandlers={{
									click: () => {
										postName(ele.id, ele.name);
									},
								}}
							/>
						);
					})}
			</MapContainer>
		</>
	);
}
