package com.develop.daniil.securesms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class NumberActivity extends AppCompatActivity {

    ImageView logo; ImageButton newMsg; TextView appName; Button next; EditText number; String prefix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        /*
            Work with my_toolbar
        */
        logo = findViewById(R.id.logo);
        newMsg = findViewById(R.id.newMsg);
        appName = findViewById(R.id.appName);
        number = findViewById(R.id.number_EditText);

        logo.setImageResource(R.drawable.ic_arrow_back_black_24dp); //Кнопка назад вместо Лого
        appName.setText("Новое сообщение");
        newMsg.setVisibility(View.GONE); //Убрать нью мсдж

        logo.setOnClickListener(new View.OnClickListener() { // =Arrow-back
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*
            Work with Activity
         */

        String[] data = {
                "Russia (+7)",
                "Ukraine (+380)",
                "Belarus (+375)",
                "Kazakhstan (+77)",
                "Azerbaijan (+994)",
                "Armenia (+374)",
                "Georgia (+995)",
                "Israel (+972)",
                "USA (+1)",
                "Germany (+49)",
                "Kyrgyzstan (+996)",
                "Latvia (+371)",
                "Lithuania (+370)",
                "Estonia (+372)",
                "Moldova (+373)",
                "Tajikistan (+992)",
                "Turkmenistan (+993)",
                "Uzbekistan (+998)",
                "Afghanistan (+93)",
                "Albania (+355)",
                "Algeria (+21)",
                "Algeria (+213)",
                "American Samoa (+1684)",
                "Andorra (+376)",
                "Angola (+244)",
                "Anguilla (+1264)",
                "Antigua and Barbuda (+1268)",
                "Argentina (+54)",
                "Aruba (+297)",
                "Australia (+61)",
                "Australia (+672)",
                "Austria (+43)",
                "Bahamas (+1242)",
                "Bahrain (+973)",
                "Bangladesh (+880)",
                "Barbados (+1246)",
                "Belgium (+32)",
                "Belize (+501)",
                "Benin (+229)",
                "Bermuda (+1441)",
                "Bhutan (+975)",
                "Bolivia (+591)",
                "Bonaire, Sint Eustatius and Saba (+1721)",
                "Bosnia and Herzegovina (+387)",
                "Botswana (+267)",
                "Brazil (+55)",
                "British Virgin Islands (+1284)",
                "Brunei (+673)",
                "Bulgaria (+359)",
                "Burkina Faso (+226)",
                "Burundi (+257)",
                "Ivory Coast (+225)",
                "Cambodia (+855)",
                "Cameroon (+237)",
                "Canada (+1)",
                "Cape Verde (+238)",
                "Cayman Islands (+1345)",
                "Central African Republic (+236)",
                "Chad (+235)",
                "Chile (+56)",
                "China (+86)",
                "Colombia (+57)",
                "Comoros (+269)",
                "Congo (+242)",
                "Congo, Democratic Republic (+243)",
                "Cook Islands (+682)",
                "Costa Rica (+506)",
                "Croatia (+385)",
                "Cuba (+53)",
                "Curaçao (+5999)",
                "Cyprus (+357)",
                "Czech Republic (+420)",
                "Denmark (+45)",
                "Djibouti (+253)",
                "Dominica (+1767)",
                "Dominican Republic (+1809)",
                "Dominican Republic (+1829)",
                "Dominican Republic (+1849)",
                "East Timor (+670)",
                "Ecuador (+593)",
                "Egypt (+20)",
                "El Salvador (+503)",
                "Equatorial Guinea (+240)",
                "Eritrea (+291)",
                "Ethiopia (+251)",
                "Falkland Islands (+500)",
                "Faroe Islands (+298)",
                "Fiji (+679)",
                "Finland (+358)",
                "France (+33)",
                "French Guiana (+594)",
                "French Polynesia (+689)",
                "Gabon (+241)",
                "Gambia (+220)",
                "Ghana (+233)",
                "Gibraltar (+350)",
                "Greece (+30)",
                "Greenland (+299)",
                "Grenada (+1473)",
                "Guadeloupe (+590)",
                "Guam (+1671)",
                "Guatemala (+502)",
                "Guinea (+224)",
                "Guinea-Bissau (+245)",
                "Guyana (+592)",
                "Haiti (+509)",
                "Honduras (+504)",
                "Hungary (+36)",
                "Iceland (+354)",
                "Iceland (+403)",
                "Iceland (+404)",
                "India (+91)",
                "Indonesia (+62)",
                "Iran (+98)",
                "Iraq (+964)",
                "Ireland (+353)",
                "Isle of Man (+44)",
                "Italy (+39)",
                "Jamaica (+1876)",
                "Japan (+81)",
                "Jordan (+962)",
                "Kenya (+254)",
                "Kiribati (+686)",
                "Kuwait (+965)",
                "Laos (+856)",
                "Lebanon (+961)",
                "Lesotho (+266)",
                "Liberia (+231)",
                "Libya (+218)",
                "Liechtenstein (+423)",
                "Luxembourg (+352)",
                "Macedonia (+389)",
                "Madagascar (+261)",
                "Malawi (+265)",
                "Malaysia (+60)",
                "Maldives (+960)",
                "Mali (+223)",
                "Malta (+356)",
                "Marshall Islands (+692)",
                "Martinique (+596)",
                "Mauritania (+222)",
                "Mauritius (+230)",
                "Mexico (+52)",
                "Micronesia (+691)",
                "Monaco (+377)",
                "Mongolia (+976)",
                "Montenegro (+382)",
                "Montserrat (+1664)",
                "Morocco (+212)",
                "Mozambique (+258)",
                "Myanmar (+95)",
                "Namibia (+264)",
                "Nauru (+674)",
                "Nepal (+977)",
                "Netherlands (+31)",
                "New Caledonia (+687)",
                "New Zealand (+64)",
                "Nicaragua (+505)",
                "Niger (+227)",
                "Nigeria (+234)",
                "Niue (+683)",
                "Norfolk Island (+6723)",
                "North Korea (+850)",
                "Northern Mariana Islands (+1670)",
                "Norway (+47)",
                "Oman (+968)",
                "Pakistan (+92)",
                "Palau (+680)",
                "Palestine (+970)",
                "Panama (+507)",
                "Papua New Guinea (+675)",
                "Paraguay (+595)",
                "Peru (+51)",
                "Philippines (+63)",
                "Pitcairn Islands (+64)",
                "Poland (+48)",
                "Portugal (+351)",
                "Puerto Rico (+1787)",
                "Puerto Rico (+1939)",
                "Qatar (+974)",
                "Réunion (+262)",
                "Romania (+40)",
                "Rwanda (+250)",
                "Democratic Republic of\n" +
                        "São Tomé and Príncipe (+239)",
                "Saint Helena (+247)",
                "Saint Helena (+290)",
                "Saint Kitts and Nevis (+1869)",
                "Saint Lucia (+1758)",
                "Saint Pierre and Miquelon (+508)",
                "Saint Vincent and the Grenadines (+1784)",
                "Samoa (+685)",
                "San Marino (+378)",
                "Saudi Arabia (+966)",
                "Senegal (+221)",
                "Serbia (+381)",
                "Seychelles (+248)",
                "Sierra Leone (+232)",
                "Singapore (+65)",
                "Sint Maarten (+599)",
                "Slovakia (+421)",
                "Slovenia (+386)",
                "Solomon Islands (+677)",
                "Somalia (+252)",
                "South Africa (+27)",
                "South Korea (+82)",
                "South Sudan (+211)",
                "Spain (+34)",
                "Sri Lanka (+94)",
                "Sudan (+249)",
                "Suriname (+597)",
                "Svalbard and Jan Mayen (+47)",
                "Swaziland (+268)",
                "Sweden (+46)",
                "Switzerland (+41)",
                "Syria (+963)",
                "Taiwan (+886)",
                "Tanzania (+255)",
                "Thailand (+66)",
                "Togo (+228)",
                "Tokelau (+690)",
                "Tonga (+676)",
                "Trinidad and Tobago (+1868)",
                "Tunisia (+216)",
                "Turkey (+90)",
                "Turks and Caicos Islands (+1649)",
                "Tuvalu (+688)",
                "US Virgin Islands (+1340)",
                "Uganda (+256)",
                "United Arab Emirates (+971)",
                "United Kingdom (+44)",
                "United Kingdom (+246)",
                "Uruguay (+598)",
                "Vanuatu (+678)",
                "Venezuela (+58)",
                "Vietnam (+84)",
                "Wallis and Futuna (+681)",
                "Yemen (+967)",
                "Zambia (+260)",
                "Zimbabwe (+263)",
                "Macao (+853)",
                "HongKong (+852)",
        };

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        number = findViewById(R.id.number_EditText);
        number.requestFocus(); //set focus by default

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // выделяем элемент
        spinner.setSelection(0);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
//                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                prefix = parent.getItemAtPosition(position).toString();

                prefix = prefix.replaceAll("[a-zA-Z ]*", ""); //ТОЛЬКО цифры

                number.setText(prefix + " ");
                Selection.setSelection(number.getText(), number.getText().length());


                number.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(!s.toString().startsWith(prefix + " ")){
                            number.setText(prefix + " ");
                            Selection.setSelection(number.getText(), number.getText().length());

                        }

                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        next = findViewById(R.id.next);


        next.setOnClickListener(new View.OnClickListener() { //Кнопка Далее
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NumberActivity.this, ChatActivity.class);

                String num = number.getText().toString();
                num = num.replaceAll("[()]", "");

                num = "5554";
                intent.putExtra("number", num); // send Username to Chat

               // if((number.getText().length() - prefix.length() - 1 > 0) &&(number.getText().length() <= 20)) //Todo: Проверка номера
              //  {
                    finish();
                    startActivity(intent);
             //   }
              //  else {
             //       Toast.makeText(NumberActivity.this, "Укажите номер в поле ввода", Toast.LENGTH_SHORT).show();
             //   }
            }
        });
    }
}
