document.addEventListener('DOMContentLoaded', () => {
    const canvas = document.getElementById('myCanvas');
    if (canvas) {
        const width = window.innerWidth;
        const height = window.innerHeight;

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
            new City("Mitú", 1.252178, -70.233604, 16422, 'green'),
            new City("Taraira", -0.564548, -69.634123, 6619, 'black')
        ];

        const ctx = canvas.getContext('2d');

        canvas.width = width;
        canvas.height = height;

        let maxLat = -90;
        let minLat = 90;
        let maxArea = 0;
        cities.forEach(city => {
            maxLat = Math.max(maxLat, city.latitude);
            minLat = Math.min(minLat, city.latitude);
            maxArea = Math.max(maxArea, city.area);
        });

        function getRandomColor() {
            const letters = '0123456789ABCDEF';
            let color = '#';
            for (let i = 0; i < 6; i++) {
                color += letters[Math.floor(Math.random() * 16)];
            }
            return color;
        }

        function geoToCanvas(latitude, longitude, area, maxWidth, maxHeight) {
            const normalizedLat = (latitude - minLat) / (maxLat - minLat);

            const scaleX = maxWidth / 360;

            const scale = maxWidth < maxHeight ? scaleX : maxWidth / (maxLat - minLat);

            const x = (longitude + 180) * scale;
            const y = (1 - normalizedLat) * maxHeight;

            const maxSize = 50;
            const size = Math.sqrt(area / maxArea) * maxSize;

            return { x, y, size };
        }

        cities.forEach(city => {
            const { x, y, size } = geoToCanvas(city.latitude, city.longitude, city.area, canvas.width, canvas.height);
            ctx.fillStyle = city.color;
            ctx.fillRect(x - size / 2, y - size / 2, size, size);

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
        });

        const cityInfo = document.createElement('div');
        cityInfo.id = 'cityInfo';
        document.body.appendChild(cityInfo);
    } else {
        console.error('El elemento canvas no se encontró.');
    }
});