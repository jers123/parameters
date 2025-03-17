document.addEventListener('DOMContentLoaded', () => {
    const canvas = document.getElementById('myCanvas');
    if (canvas) {
        let height = window.innerHeight;
        let width = window.innerWidth;
        
        function setDimensionCanvas(rangeLat, rangeLon) {
            const rangeLat2 = rangeLat * 2;
            if(rangeLon < rangeLat2) {
                width = Math.trunc((rangeLon * height) / rangeLat2);
            } else {
                //width = Math.trunc((rangeLon * height) / rangeLat2);
            }
            console.log("D = " + width + " X " + height);
            canvas.width = width;
            canvas.height = height;
        }
        
        class City {
            constructor(name, latitude, longitude, area, color) {
                this.name = name;
                this.longitude = longitude;
                this.latitude = latitude;
                this.area = area;
                this.color = color;
            }
        }

        let cities = [
            new City("Carurú", 1.012655, -71.296511, 6982, 'blue'),
            new City("Mitú", 1.252178, -70.233604, 16422, 'greenyellow'),
            new City("Taraira", -0.564548, -69.634123, 6619, 'black')
        ];

        const ctx = canvas.getContext('2d');

        let maxLat = -90;
        let minLat = 90;
        let maxLon = -180;
        let minLon = 180;
        let maxArea = 0;
        cities.forEach(city => {
            maxLat = Math.max(maxLat, city.latitude);
            minLat = Math.min(minLat, city.latitude);
            maxLon = Math.max(maxLon, city.longitude);
            minLon = Math.min(minLon, city.longitude);
            maxArea = Math.max(maxArea, city.area);
        });

        function geoToCanvas() {
            const rangeLat = maxLat - minLat;
            const rangeLon = maxLon - minLon;
            setDimensionCanvas(rangeLat, rangeLon);
            let relative = 0;
            let x = 0;
            let y = 0;
            console.log(rangeLon + " X " + rangeLat);
            cities.forEach(city => {
                relative = (city.latitude - minLat) / rangeLat;
                y = Math.trunc(height - (relative * height));
                relative = (city.longitude - minLon) / rangeLon;
                //x = Math.trunc(width - (relative * width));
                x = Math.trunc(relative * width);
                let {x1, y1} = refreshPoints(x, y);
                x = x1;
                y = y1;
                ctx.fillStyle = city.color;
                ctx.fillRect(x, y, 20, 20);
                console.log(city.name + ": " + city.longitude + " X " + city.latitude);
                console.log(x + " X " + y);
                console.log("-----------------------------------------------");
            });
        }

        function refreshPoints(x, y) {
            let x1 = x;
            let y1 = y
            //console.log(city.name + ": Y " + y + " X " + (height - 20));
            if(y1 >= (height - 20)) {
                y1 = height - 20;
            }
            if(x1 >= (width - 20)) {
                x1 = width - 20;
            }
            return {x1, y1};
        }

        geoToCanvas();
        
        /*let x1 = 0;
        
        cities.forEach(city => {
            const { x, y, size } = geoToCanvas(city.latitude, city.longitude, city.area, canvas.width, canvas.height);
            ctx.fillStyle = city.color;
            //ctx.fillRect(x - size / 2, y - size / 2, size, size);
            ctx.fillRect(x1, x1, 20, 20);
            x1 = x1 + 40;

            canvas.addEventListener('mousemove', (event) => {
                const mouseX = event.clientX;
                const mouseY = event.clientY;
                cities.forEach(city => {
                    const { x, y, size } = geoToCanvas(city.latitude, city.longitude, city.area, canvas.width, canvas.height);
                    if (mouseX >= x - size / 2 && mouseX <= x + size / 2 && mouseY >= y - size / 2 && mouseY <= y + size / 2) {
                        document.getElementById('cityInfo').textContent = city.name;
                    }
                });
            });
        });*/
    } else {
        alert('The element canvas not found.');
    }
});