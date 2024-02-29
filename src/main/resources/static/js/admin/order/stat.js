window.addEventListener("DOMContentLoaded", function() {
    const ctx = document.getElementById("chart");
    const data = JSON.parse(document.getElementById("json").value);
    let labels = Object.keys(data);

    labels = labels.sort((a, b) => Number(a.replace(/-/g, "")) - Number(b.replace(/-/g, "")));

    const sortedData = {};
    for (const label of labels) {
        sortedData[label] = data[label];
    }

    const values = Object.values(sortedData);

    const title = frmSearch.type.value === 'MONTH' ? "매출 월별 통계" : "매출 일별 통계";
    if (labels && labels.length > 0) {
    new Chart(ctx, {
        type: 'line',
        data: {
          labels: labels,
          datasets: [{
            label: title,
            data: values,
            borderWidth: 1
          }]
        },
        options: {
          scales: {
            y: {
              beginAtZero: true
            }
          }
        }
      });
    }
});