/*ymaps.ready(init);

var myMap, myPlacemark, point_1, point_2, price;

function init()
{
	myMap = new ymaps.Map("map", 
	{
		center: [51.66,39.20],
		zoom: 11,
		controls: ['zoomControl']
	});	
}

function cost()
{
	point_1 = document.getElementById("pointFrom").value;
	point_2 = document.getElementById("pointTo").value;
	var weight = document.getElementById("weight").value;

	ymaps.geocode(point_1, {
		results: 1
	}).then(function (res) {
		var firstGeoObject = res.geoObjects.get(0),
		f_coords = res.geoObjects.get(0).geometry.getCoordinates();

		myMap.geoObjects.add(firstGeoObject);
		console.log('Data firstGeoObject: ', firstGeoObject.properties.getAll());

		ymaps.geocode(point_2, {
			results: 1
		}).then(function (res) {
			var secondGeoObject = res.geoObjects.get(0),
			s_coords = res.geoObjects.get(0).geometry.getCoordinates();

			myMap.geoObjects.add(secondGeoObject);
			console.log('Data secondGeoObject: ', secondGeoObject.properties.getAll());

			var distance = ymaps.formatter.distance(
				ymaps.coordSystem.geo.getDistance(f_coords, s_coords)
				);
        	//document.getElementById("distance").innerHTML = distance;

        	price = parseInt(distance) * weight;
        	document.getElementById("price").value = price + " rub";
        });

	});
	var multiRoute = new ymaps.multiRouter.MultiRoute({
		referencePoints: [
		point_1,
		point_2,
		]
	}, {
		boundsAutoApply: true,
	});
	myMap.geoObjects.add(multiRoute);	
}

function showFun(id) {
	display = document.getElementById(id).style.display;
	if (display=='none')
	{
		document.getElementById(id).style.display='block';
	}
	else
	{
		document.getElementById(id).style.display='none';
	}

}*/
