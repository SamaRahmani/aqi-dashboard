function loadDashboard() {
  const city = document.getElementById("cityInput").value.trim();

  if (!city) {
    alert("Please enter a city");
    return;
  }

  // Loading state
  document.getElementById("aqiValue").innerText = "...";
  document.getElementById("aqiStatus").innerText = "Loading...";

  // fetch(`/api/dashboard?city=${city}`)
  fetch(`https://YOUR_BACKEND_URL/api/dashboard?city=${city}`)

    .then(res => {
      if (!res.ok) throw new Error("API error");
      return res.json();
    })
    .then(data => {
      document.getElementById("location").innerText = data.location;
      document.getElementById("time").innerText =
        "Last updated: " + new Date().toLocaleString();

      document.getElementById("aqiValue").innerText = data.aqi.value;
      document.getElementById("aqiStatus").innerText = data.aqi.status;
      document.getElementById("temp").innerText = data.weather.temp;
      document.getElementById("humidity").innerText = data.weather.humidity;
      document.getElementById("wind").innerText = data.weather.wind;
      document.getElementById("condition").innerText = data.weather.condition;

      setAqiColor(data.aqi.status);
    })
    .catch(() => {
      alert("City not found or server error");
    });
}

function setAqiColor(status) {
  const card = document.querySelector(".aqi-card");
  card.className = "aqi-card";

  if (status === "Good") card.classList.add("good");
  else if (status === "Fair") card.classList.add("fair");
  else if (status === "Moderate") card.classList.add("moderate");
  else if (status === "Poor") card.classList.add("poor");
  else card.classList.add("very-poor");
}
