# Grocery-CompariList
app designed to allow users to compare prices for similar products across 4 large australian grocery stores. 
data is gathered using a webcrawler (https://github.com/jmuir5/webcawler) then hosted on firebase.
the app downloads the data if it doesnt already have it, updating automatically on launch as new data is uploaded to firebase. 

development has halted as i realised products are priced incosistanlty across stores in different locations and i dint have a good solution to handle this discrepancy.
i had more polish and feature ideas i wanted to implemtn but due to this fundamental flaw in concept they go unrealised. 

this project was built in kotlin, utilising jetpack compose, and the ObjextBox database.
