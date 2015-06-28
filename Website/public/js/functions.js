function areachart(symbol, data){
    var text = symbol + ' Stock Price';
        $('#areachart').highcharts('StockChart',{

            rangeSelector : {
                selected : 1
            },

            title : {
                text : text
            },
           
             series: [{
                name: text,
                data: data
            }]
        });
}
function columchart(symbol, data){
    var text = symbol + ' Stock Volume';
    //var data = [8,2,3,5,4,6,2,4,6,12,3,1,4,5,9]
    $('#columchart').highcharts('StockChart',{
        chart: {
            alignTicks: false
        },

        rangeSelector: {
            selected: 1
        },

        title: {
            text: text
        },

        series: [{
            type: 'column',
            name: text,
            data: data,
            dataGrouping: {
                units: [[
                    'week', // unit name
                    [1] // allowed multiples
                ], [
                    'month',
                    [1, 2, 3, 4, 6]
                ]]
            }
        }]
    });
}


function candlestick(symbol, data) {	
    var text = symbol + ' Candlestick';
	$('#candlestick').highcharts('StockChart', {
		

		rangeSelector : {
			selected : 1
		},

		title : {
			text : text			
        },

		series : [{
			type : 'candlestick',
			name : text,
			data : data,
			dataGrouping : {
				units : [
					['week', // unit name
					[1] // allowed multiples
				], [
					'month', 
					[1, 2, 3, 4, 6]]
				]
			}
		}]
	});
}

