// Puts the sent data into documents in the database with our wanted format. Creating new documents for new symbols
// and updates already existing ones.

function(doc, req) {
        var jsn = JSON.parse(req.body);
        var Symbol;
        var Time;
        var Open;
        var High;
        var Low;
        var Close;
        var Volume;
       
        Symbol = jsn.Stock.Symbol;
        Time = jsn.Stock.Time;
        Open = jsn.Stock.Open;
        High = jsn.Stock.High;
        Low = jsn.Stock.Low;
        Close = jsn.Stock.Close;
        Volume = jsn.Stock.Volume;

        if(!doc) {
            if (req.id) {
                var json = {_id : req.id};
                json["Stock"] = JSON.parse("{" + "\"Data\":" + " [{\"Time\":" + "\"" + Time + "\"" + ","
                                            + "\"Open\":" + "\"" + Open + "\"" + ","
                                            + "\"High\":" + "\"" + High + "\"" + ","
                                            + "\"Low\":" + "\"" + Low + "\"" + ","
                                            + "\"Close\":" + "\"" + Close + "\"" + ","
                                            + "\"Volume\":"+ "\"" + Volume + "\"" + "}]"+ "}");
                return [json, 'New Doc Created'] }
        return [null, 'Empty World'];}
        doc.Stock.Data.push(JSON.parse("{"+ "\"Time\":" + "\"" + Time + "\"" + ","
                                        + "\"Open\":" + "\"" + Open + "\"" + ","
                                        + "\"High\":" + "\"" + High + "\"" + ","
                                        + "\"Low\":" + "\"" + Low + "\"" + ","
                                        + "\"Close\":" + "\"" + Close + "\"" + ","
                                        + "\"Volume\":"+ "\"" + Volume + "\"" +"}"));
               
        return[doc, 'Document Updated'];
}
