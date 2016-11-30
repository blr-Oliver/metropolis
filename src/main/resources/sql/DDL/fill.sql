INSERT INTO category(id, display_name, scope_display_name, element_display_name, id_parent) VALUES (0,'Павильоны','Павильоны','павильон',NULL),(1,'Магазины','Магазины','магазин',0),(2,'Магазины одежды','одежды','магазин одежды',1),(3,'Магазины обуви','обуви','магазин обуви',1),(4,'Магазины косметики','косметики','магазин косметики',1),(5,'Магазины белья','белья','магазин белья',1),(6,'Магазины техники','техники','магазин техники',1),(7,'Магазины кожаных изделий','кожаных изделий','магазин кожаных изделий',1),(8,'Универсальные магазины','универсальные','универсам',1),(9,'Магазины головных уборов','головных уборов','магазин головных уборов',1),(10,'Магазины посуды','посуды','магазин посуды',1),(11,'Магазины мебели','мебели','магазин мебели',1),(12,'Магазины алкогольной продукции','алкоголя','магазин алкогольной продукции',1),(13,'Питание','Питание','место питания',0),(14,'Кафе','Кафе','кафе',13),(15,'Пиццерии','Пиццерии','пиццерия',13),(16,'Рестораны','Рестораны','ресторан',13),(17,'Услуги','Услуги','центр услуг',0),(18,'Салоны красоты','Салоны красоты','салон красоты',17),(19,'Финансовые услуги','Финансовые услуги','центр финансовых услуг',17),(20,'Магазины мужской одежды','мужской','магазин мужской одежды',2),(21,'Магазины женской одежды','женской','магазин женской одежды',2),(22,'Магазины мобильных телефонов','мобильников','магазин мобильных телефонов',6),(23,'Ювелирные магазины','ювелирные','ювелирный магазин',1),(24,'Галантерея','галантерея','галантерея',1);
INSERT INTO attribute(id, display_name, type) VALUES (0,'Доставка',2),(1,'Справка по телефону',0),(2,'Оплата карточкой',2),(3,'Пробники',0),(4,'Консультация',1);
INSERT INTO attribute_value(id, display_name) VALUES (0,'Visa'),(1,'Maestro'),(2,'MasterCard'),(3,'по Минску'),(4,'по РБ'),(5,'платная'),(6,'бесплатная');
INSERT INTO attribute_choice(id_attribute, id_value, ordinal) VALUES (0,3,0),(0,4,1),(2,0,1),(2,1,0),(2,2,2),(4,5,0),(4,6,1);
INSERT INTO category_attribute(id_category, id_attribute) VALUES (0,0),(0,1),(0,2),(0,4),(1,2),(2,2),(3,2),(4,2),(4,3),(4,4),(5,2),(6,2),(6,4),(7,2),(8,2),(9,2),(10,2),(11,2),(11,4),(12,2),(13,1),(13,2),(14,1),(14,2),(15,1),(15,2),(16,2),(17,2),(17,4),(18,2),(18,4),(19,2),(20,2),(21,2),(22,1),(22,2),(22,4),(23,2),(23,4);
INSERT INTO tag(id, display_name) VALUES (0,'одежда'),(1,'мужская-одежда'),(2,'женская-одежда'),(3,'обувь'),(4,'мужская-обувь'),(5,'женская-обувь'),(6,'спортивная-обувь'),(7,'зимняя-обувь'),(8,'спортивная-одежда'),(9,'футболки'),(10,'женское-белье'),(11,'мужское-белье'),(12,'белье'),(13,'постель'),(14,'постельное-белье'),(15,'косметика'),(16,'парфюмерия'),(17,'шампуни'),(18,'уход-за-волосами'),(19,'кремы'),(20,'спа'),(21,'куртки'),(22,'сумки'),(23,'женские-костюмы'),(24,'мужские-костюмы'),(25,'рубашки'),(26,'трусы'),(27,'сорочки'),(28,'бюстгалтеры'),(29,'носки'),(30,'мужские-туфли'),(31,'женские-туфли'),(32,'сапоги'),(33,'женские-сапоги'),(34,'бижутерия'),(35,'украшения'),(36,'часы'),(37,'золото'),(38,'компьютеры'),(39,'трикотаж'),(40,'верхняя-одежда'),(41,'пальто'),(42,'шапки'),(43,'кепки'),(44,'шарфы'),(45,'бытовая-техника'),(46,'телефоны'),(47,'мебель'),(48,'детская-одежда'),(49,'детская-обувь'),(50,'игрушки'),(51,'сувениры'),(52,'платья');
INSERT INTO category_tag(id_category, id_tag) VALUES (2,0),(2,1),(2,2),(2,8),(2,9),(2,21),(2,23),(2,24),(2,25),(2,40),(2,48),(3,3),(3,4),(3,5),(3,6),(3,7),(3,30),(3,31),(3,32),(3,33),(3,49),(4,15),(4,16),(4,17),(4,18),(4,19),(4,20),(5,10),(5,11),(5,12),(5,13),(5,14),(6,38),(6,45),(6,46),(7,0),(7,3),(7,21),(7,22),(7,32),(7,33),(7,40),(9,42),(9,43),(20,1),(20,9),(20,25),(20,29),(20,41),(21,2),(21,27),(21,39),(22,46),(23,34),(23,35),(23,36),(23,37),(23,51);
INSERT INTO brand(id, display_name) VALUES (0,'Dove'),(1,'Guerlain'),(2,'Dior'),(3,'D&G'),(4,'Chanel'),(5,'Armani'),(6,'Lancome'),(7,'Essence'),(8,'Avon'),(9,'Nivea'),(10,'L\'oreal'),(11,'Adidas'),(12,'Nike'),(13,'Puma'),(14,'Reebok'),(15,'Umbro'),(16,'Camelot'),(17,'El Tempo'),(18,'Monarch'),(19,'Ecco'),(20,'Salamander'),(21,'Prada'),(22,'Armani'),(23,'Merrell'),(24,'Sorel'),(25,'Calvin Klein'),(26,'Tom Tailor'),(27,'Crown'),(28,'Levis\'s'),(29,'Seven Jeans'),(30,'Arizona'),(31,'Laura Scott'),(32,'Diesel'),(33,'Tom Farr'),(34,'Tommy Hilfiger'),(35,'Seeberger'),(36,'Wegener'),(37,'Baon'),(38,'E-Bound'),(39,'Charmante'),(40,'Animal'),(41,'Мiлавiца'),(42,'Serge'),(43,'Felina'),(44,'Dimanche'),(45,'Helena'),(46,'Millena');
INSERT INTO shop(id, id_category,display_name,description) VALUES (0,4,'бп0ве7й51ыьэрщювбсгы5юлфвкбщм7',NULL),(1,6,'жяз2ож8цоийз0лб3ом2д3ажфъы1бмъ',NULL),(2,11,'50нциуетв282нкк0о03окчяуалую63',NULL),(3,14,'53лэсз2лгцябрцца5и4слцим4гщдпм',NULL),(4,1,'щбкз1мгмф1яы763у74д1й62чыц1осн',NULL),(5,13,'мфудз1с4ущ5гтчокфхаюдхлид0щфне',NULL),(6,7,'пдсяп3сгшя4хфияыпгк4жбщшнб7зчу',NULL),(7,22,'хюб73омыо56пщх2дъьщм2йружбу1ш7',NULL),(8,9,'021я7щэяргыийж1яп780п9р96ыяцуш',NULL),(9,4,'576дъбюцилявн21кхс34щя9ьйиупи1',NULL),(10,4,'ржб7рь2жцэгкйщнэфыфо1зхч2шрчтй',NULL),(11,16,'5сю3пжщм1пщч1пчнв9э1нгьввуйла2',NULL),(12,9,'ъжя2хчьизэл7чьр3биарпджыз0довк',NULL),(13,11,'ьэ7аць2юхйхе115югвулкт86иь3в8а',NULL),(14,1,'у18доэъеео2еъен02эп1дцшхжэж8цн',NULL),(15,1,'2э44йнцдбхкер8нйъечхъжсьи31х4щ',NULL),(16,8,'наж7сй6фзоэкейш80рхъфоги9пщ0х2',NULL),(17,0,'бу7шойыфцз25й5жцлт2в51у0мьтв74',NULL),(18,3,'нкьоь4итм1рт41бмжьф3ъвеа87уйзж',NULL),(19,1,'3плг8хб4бц56ччгучндаяхо4ып0хук',NULL),(20,7,'ум624ъъфэачщвжну4еййюьгвг9ь0иы',NULL),(21,12,'зе6ш8хъ9е84бъюэ3бжм1й2о1дыэьты',NULL),(22,6,'ыфгц16стсе6мбяжо7фоз5дйт2ъьябш',NULL),(23,11,'9явжщ1пх2иьйщм5и3ы8э5пи21р6ння',NULL),(24,21,'8иж27аййймч2я9са8н1бфоев6ц2йщу',NULL),(25,13,'чх3у5докйв1чфжа3пйньхдй0двк0ь7',NULL),(26,7,'юг1л74евсг1яирщжеч2ж5длгипб6ко',NULL),(27,15,'зрщуль4с9фреидр4ж65выз2ю3ууиэ5',NULL),(28,20,'щ3цяд7ь7уввжейс8фонэжыюнчгл0мц',NULL),(29,4,'999з83яслцсм70шхмк9щчтжеряевго',NULL),(30,18,'фэащыкй4в9мврщ0цауъкс3цл2ыаы2м',NULL),(31,3,'дш9лшжюйаьв8034эф2яльчжсхю6узс',NULL),(32,19,'йц45бвдтдгпфябжьрс1хю5фытйс2ьз',NULL),(33,23,'ффчегхпощлпзмфсш326нуябеълэющ4',NULL),(34,12,'к7гщэф7ю1л0кй6вце3ф8мьк20п9я5м',NULL),(35,16,'звэфгракъ2нхцжфц31си47ф9квыяэ9',NULL),(36,1,'аоуавщз4ифю4вяесффецчр2ещ9мк8н',NULL),(37,17,'м9479296ц47дк7вътоувр7а14црщву',NULL),(38,20,'одаьбв7зкъ9рр4ойгьчййсв6тыньоь',NULL),(39,1,'ры4а2гщонд35киъчвшчкккмь37хп0д',NULL),(40,3,'у3жэю4юеьшыщонж749г7с7ну5шщеи7',NULL),(41,13,'ъ4ю9дй04ыбшпдабця2щбтмс16дъуб9',NULL),(42,3,'3ддсдпящдовху62дет1чыф5пэ7мсбр',NULL),(43,22,'хчвьшо8шбж0лоа4аязэкюзак57ю7н8',NULL),(44,10,'щ0йши51зьбтоххрф2ън1щжрбч045о7',NULL),(45,10,'дыемьме74иизд4нмюьсйьзщб8б1ид5',NULL),(46,4,'щж8кяг65щ8й2ьэчп1дзецэй6гъ0йй7',NULL),(47,22,'ьщдог0вдколюг6ъ39щсакы1паь4чею',NULL),(48,20,'2тъ4щъкэ7негз30шчас6йужчгс3с4л',NULL),(49,8,'феаюянв4нфщъб2хл3етюяиа7тпс8ца',NULL),(50,9,'3шгьккм0ькьвжсьжйщж6уф9ркацоъъ',NULL),(51,3,'е44айсс9рбы4югйфх2гхрб9д6ачцсз',NULL),(52,21,'н0ъз73жчлфи57й0т7дюж4кш0т9ыцсщ',NULL),(53,9,'шякжи3рз4нщ7кксйь98цчмяаицн0юа',NULL),(54,20,'6одсйозэлл1йкщ3мплщюе34бьсчюыи',NULL),(55,15,'ожэшьщ9щйеыезомм38лй6с959ас8жк',NULL),(56,2,'бжуцщ3ззыояй8ьэецщцъ63мочевщ8с',NULL),(57,10,'ф5оэ150ф91ъжофащпюыжаь07м157яи',NULL),(58,23,'зчтждцм70л1вншаям824йьаюяиел7д',NULL),(59,21,'бгщлкйс2сцзщи8юга440у93пршгцз5',NULL),(60,17,'ъ809нхудръдсл872и8тэуияль0що4р',NULL),(61,8,'узьоа9зс2жьйыб1сятия1мнрпшрбмй',NULL),(62,1,'чгапхуил7бм24ж8тло0сааежжочошч',NULL),(63,11,'437пй37шх1б7юрйцышуу5оо4ятюжф1',NULL),(64,8,'йпьммх53чпниещэжщ8еефмуэбимь5ю',NULL),(65,8,'хюбьшыицхгютсааеи7е8а6озя9196р',NULL),(66,6,'4лчмхяпье276ядзефюнтшлтжркв26б',NULL),(67,3,'нукохзд55х35ъчжъткгзяри0б7юуцх',NULL),(68,9,'4гнхбсщне9ъщ10гкм4оц26уйижысад',NULL),(69,15,'ъ8962юф9д2ткъг2о49о42есаеыен21',NULL),(70,9,'15ж5лэяьр1ньд5вшпе9шмсб3ъиозуи',NULL),(71,0,'9щчв4062ибощы1иъжзаху1з47щюффш',NULL),(72,15,'97дщи9дд45п14ющшюить7иъкв4ач5ы',NULL),(73,0,'члаэуц8ььь9сжчвт1анб6чвущшхыщр',NULL),(74,8,'ч9цж2ч8ей0ун6б8сд9к7жрж8фянх5в',NULL),(75,13,'хч5в4п8ъ32ьчб8шо313щвсббеэвъ0и',NULL),(76,1,'ч5ьясдпэпз3л6мп1гле3т5е9зйчщйх',NULL),(77,4,'70ъщыбйежьпткб2яцщаа68ты1фзтъж',NULL),(78,13,'сцэзъваьфлдчс1и7цзк2хщ89ыфт1сз',NULL),(79,9,'вю05щдэ3шетюш5рпы1ь0фщпевю4внф',NULL),(80,23,'юо70шд2359з5гтл2дтязтткщйгчкшы',NULL),(81,11,'ихчмэчазниыщ0бфэмйрдкмб30тявч7',NULL),(82,4,'щ2рэув4чдш1н1ц4ы9е1фхьуш8лц5ък',NULL),(83,23,'щб5гжу8з7овс6ьсссш0м3аыржзрд0ж',NULL),(84,5,'э8кы0у2ся0ах5юыйи9язсфгъхгыъыю',NULL),(85,8,'ушсювэц4ы2пфы4йзу6е5з5вщтк8яти',NULL),(86,13,'ящщ8ль6ц8ьчиакы14жцлзюява3сдбм',NULL),(87,18,'уг45пщыщоык3жбзжхч0гэдпон6мщеэ',NULL),(88,11,'ясл5й0ф4сбпф1иш58сегъ2цр7нлйай',NULL),(89,3,'ммо48шрд7змдъе77щкфщт3кб63йъ3з',NULL),(90,8,'089ямбрщувч8юытфолш4о96щю89ьвб',NULL),(91,6,'7ч0щ3й0бяехувбсулф7ынидхиьщдэц',NULL),(92,14,'9ф9хръррггбъюбяуэч4нв9п72ьцтщл',NULL),(93,1,'хех0мьэ0к0о4лажыеиср3щеи9дыдбр',NULL),(94,8,'прчея56кцжцс2нс82й9уроицюеи5у4',NULL),(95,9,'рмщажй9шйбевюунц1йш72в7з7ьот70',NULL),(96,7,'ц8еш9пдшюф6юю23цпцчщш3ъфцшюу9з',NULL),(97,6,'р61ы2чп8э8едб9ы0ь4ебч65цюжм1ю4',NULL),(98,14,'гм4ссююцчиа2о1хявоуэ1цквндп0юм',NULL),(99,1,'щп1чо404юзк9у9микхцц3зуе6ъжко5',NULL),(100,1,'test','ANSI'),(101,1,'тест','UTF-8');
INSERT INTO shop_tag(id_shop, id_tag) VALUES (6,0),(20,0),(87,0),(29,1),(83,1),(94,1),(16,2),(41,3),(82,3),(5,4),(39,4),(47,4),(80,4),(25,5),(48,5),(52,5),(88,5),(70,6),(45,7),(63,7),(72,7),(56,8),(84,8),(1,9),(52,9),(63,9),(90,9),(36,10),(85,10),(4,11),(28,11),(35,11),(39,11),(47,11),(67,11),(94,11),(6,12),(42,12),(46,12),(47,12),(67,12),(75,12),(1,13),(12,13),(35,13),(69,13),(85,13),(88,13),(90,13),(97,13),(20,15),(22,15),(26,15),(40,15),(50,15),(52,15),(84,15),(88,15),(0,16),(3,16),(30,16),(23,17),(26,17),(58,18),(11,19),(23,19),(53,19),(97,19),(27,20),(44,20),(54,20),(14,21),(39,21),(71,21),(27,22),(60,22),(87,22),(47,23),(5,24),(23,24),(50,24),(79,24),(87,24),(21,25),(32,25),(37,25),(82,25),(0,26),(5,26),(14,26),(37,26),(50,26),(81,26),(83,26),(68,27),(70,27),(89,27),(97,27),(13,28),(20,28),(27,28),(55,28),(70,28),(73,28),(77,28),(79,28),(94,28),(1,29),(13,29),(14,30),(24,30),(35,30),(36,30),(72,30),(61,31),(90,31),(38,32),(60,32),(66,32),(94,32),(10,34),(68,34),(81,34),(84,34),(85,34),(1,35),(25,35),(36,35),(42,35),(51,35),(37,37),(45,37),(84,37),(93,37),(78,38),(81,39),(13,40),(23,40),(26,40),(30,40),(36,40),(39,40),(45,40),(52,40),(60,40),(70,40),(89,41),(11,42),(27,42),(37,42),(56,42),(63,42),(82,42),(40,43),(41,43),(63,43),(69,43),(88,43),(89,43),(7,44),(11,44),(14,44),(25,44),(83,45),(45,46),(57,46),(96,46),(53,47),(67,47),(79,47),(87,47),(96,47),(5,48),(42,48),(53,48),(97,48),(67,49),(79,49),(89,49),(3,50),(31,50),(61,50),(15,51),(57,52),(61,52);
INSERT INTO shop_attribute(id_shop, id_attribute, id_val) VALUES (0,0,3),(0,1,NULL),(0,2,0),(0,2,2);
INSERT INTO contact(id_shop, type, value) VALUES (0,'Телефон','375293141868'),(0,'Телефон','375172617139'),(0,'Skype','vasily.liaskovsky'),(0,'e-mail','vasily.liaskovsky@gmail.com'),(0,'e-mail','blr_Oliver@tut.by');
