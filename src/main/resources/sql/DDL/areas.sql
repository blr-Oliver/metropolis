INSERT INTO location(id, display_name) VALUES (1,'ТЦ Столица');
INSERT INTO sub_location(id, id_location, display_name, view_box, basis) VALUES(1, 1, 'Верхний уровень', '8 1 1243 546', 'm748 242c56 0 56-84 0-84-56 0-56 84 0 84zM66 317c3 37 62 95 115 95v-45h-43v-19h-35l-6-6v-58h-7v33zm697-4v22h33l4 135 18 17h-17l2 41h-11v8h-22l-8 8h-14l-8-7h-32l-1-48-20-19h19l-4-132h31v-25h-69l-12 12H385v5H208v18h50l-18 18h-4l2 93v22h25l-15 15h-47v12h-49v-9h-16l16-17h9v-26c-38 0-119-49-136-117l12-27h7l-1-27h-6v6l-20 20v-35h-6v-47h6l18-18v18h55v31h7V4h288v10h156l45 45h642l10 61h10v64l-10 3z');
INSERT INTO sub_location(id, id_location, display_name, view_box, basis) VALUES(2, 1, 'Средний уровень', '8 1 1243 546', 'm97 56h288v3h843l10 61v67L763 313h-99l-12 12H385v5H136l-32-38h-7zm651 186c56 0 56-84 0-84-56 0-56 84 0 84z');
INSERT INTO sub_location(id, id_location, display_name, view_box, basis) VALUES(3, 1, 'Нижний уровень', '8 1 1243 546', 'm97 56h288v3h843l10 61v67L763 313h-99l-12 12H385v5H136l-32-38h-7z');
INSERT INTO area(id, id_sub_location, path) VALUES(126, 1, 'm385 33h125v170H385z');
INSERT INTO area(id, id_sub_location, path) VALUES(1, 1, 'm510 59h109v144H510z');
INSERT INTO area(id, id_sub_location, path) VALUES(2, 1, 'm876 169h56v99l-56 15z');
INSERT INTO area(id, id_sub_location, path) VALUES(3, 1, 'm621 252h48v44h-48z');
INSERT INTO area(id, id_sub_location, path) VALUES(4, 1, 'm593 252h28v20h-28z');
INSERT INTO area(id, id_sub_location, path) VALUES(5, 1, 'm593 272h28v53h-28z');
INSERT INTO area(id, id_sub_location, path) VALUES(6, 1, 'm385 252h197v73H385z');
INSERT INTO area(id, id_sub_location, path) VALUES(7, 1, 'm932 169h27v44h-27z');
INSERT INTO area(id, id_sub_location, path) VALUES(8, 1, 'm959 169h28v44h-28z');
INSERT INTO area(id, id_sub_location, path) VALUES(9, 1, 'm987 169h26v44h-26z');
INSERT INTO area(id, id_sub_location, path) VALUES(10, 1, 'm1026 169h50v23h-22v27h-28z');
INSERT INTO area(id, id_sub_location, path) VALUES(11, 1, 'm1076 169h23v23h-23z');
INSERT INTO area(id, id_sub_location, path) VALUES(12, 1, 'm1099 169h28v23h-28z');
INSERT INTO area(id, id_sub_location, path) VALUES(13, 1, 'm1127 169h29v39l-29 8z');
INSERT INTO area(id, id_sub_location, path) VALUES(14, 1, 'm1156 169h27v32l-27 7z');
INSERT INTO area(id, id_sub_location, path) VALUES(15, 1, 'm1183 169h65v15l-65 17z');
INSERT INTO area(id, id_sub_location, path) VALUES(16, 1, 'm1230 164h18v5h-18z');
INSERT INTO area(id, id_sub_location, path) VALUES(17, 1, 'm1211 82h21l6 38h-27z');
INSERT INTO area(id, id_sub_location, path) VALUES(18, 1, 'm1156 59h27v23h28v38h-55z');
INSERT INTO area(id, id_sub_location, path) VALUES(19, 1, 'm1127 59h29v61h-29z');
INSERT INTO area(id, id_sub_location, path) VALUES(20, 1, 'm1099 59h28v61h-28z');
INSERT INTO area(id, id_sub_location, path) VALUES(21, 1, 'm1057 59h42v61h-73v-16h31z');
INSERT INTO area(id, id_sub_location, path) VALUES(22, 1, 'm932 59h81v61h-81z');
INSERT INTO area(id, id_sub_location, path) VALUES(23, 1, 'm876 59h56v61h-56z');
INSERT INTO area(id, id_sub_location, path) VALUES(24, 1, 'm845 59h31v61h-31z');
INSERT INTO area(id, id_sub_location, path) VALUES(25, 1, 'm817 59h28v61h-22l-6-7z');
INSERT INTO area(id, id_sub_location, path) VALUES(26, 1, 'm749 59h54v54l-7 7h-47z');
INSERT INTO area(id, id_sub_location, path) VALUES(27, 1, 'm827 169h49v99h-77c26-23 44-52 28-99z');
INSERT INTO area(id, id_sub_location, path) VALUES(28, 1, 'm669 252h13l17 18-2 15-11-1-6 12h-11z');
INSERT INTO area(id, id_sub_location, path) VALUES(29, 1, 'm363 243h22v37h-22z');
INSERT INTO area(id, id_sub_location, path) VALUES(30, 1, 'm341 243h22v37h-22z');
INSERT INTO area(id, id_sub_location, path) VALUES(31, 1, 'm320 243h21v37h-21z');
INSERT INTO area(id, id_sub_location, path) VALUES(32, 1, 'm297 243h23v37h-23z');
INSERT INTO area(id, id_sub_location, path) VALUES(33, 1, 'm245 243h52v87h-65v-11c25-20 30-50 13-76z');
INSERT INTO area(id, id_sub_location, path) VALUES(34, 1, 'm97 284h43c3 15 12 30 25 38v17h-20c-15-10-26-26-31-47H97z');
INSERT INTO area(id, id_sub_location, path) VALUES(35, 1, 'm197 247c38 0 38 55 0 55-38 0-37-55 0-55z');
INSERT INTO area(id, id_sub_location, path) VALUES(36, 1, 'm97 228h25l24 12-8 20H97z');
INSERT INTO area(id, id_sub_location, path) VALUES(37, 1, 'm97 4h288v185H142v24H97z');
INSERT INTO area(id, id_sub_location, path) VALUES(38, 1, 'm275 194h110v19H275z');
INSERT INTO area(id, id_sub_location, path) VALUES(39, 1, 'm189 194h77v19h-77z');
INSERT INTO area(id, id_sub_location, path) VALUES(40, 1, 'm142 194h47v19c-11 0-25 6-33 14l-14-14z');
INSERT INTO area(id, id_sub_location, path) VALUES(41, 1, 'm1235 102h13v18h-10z');
INSERT INTO area(id, id_sub_location, path) VALUES(42, 1, 'm1230 71h13v16h-10z');
INSERT INTO area(id, id_sub_location, path) VALUES(43, 1, 'm97 292h17c5 20 15 35 31 47h20v-9h22v18h-84l-6-6z');
INSERT INTO area(id, id_sub_location, path) VALUES(44, 1, 'm210 366h26v22h-18v-2l-8-6z');
INSERT INTO area(id, id_sub_location, path) VALUES(45, 1, 'm218 394v-6h18l1 24h-25v-12z');
INSERT INTO area(id, id_sub_location, path) VALUES(46, 1, 'm212 412h25v22h-18v-3l-7-7z');
INSERT INTO area(id, id_sub_location, path) VALUES(47, 1, 'm219 439v-5h18l1 25h-24l-1-15z');
INSERT INTO area(id, id_sub_location, path) VALUES(48, 1, 'm214 459h24v22h-24z');
INSERT INTO area(id, id_sub_location, path) VALUES(49, 1, 'm35 229h55v31H35z');
INSERT INTO area(id, id_sub_location, path) VALUES(50, 1, 'm66 284h24v33H66z');
INSERT INTO area(id, id_sub_location, path) VALUES(51, 1, 'm161 434l20 1v47h-20z');
INSERT INTO area(id, id_sub_location, path) VALUES(52, 1, 'm137 425l24 9v22l-34-9z');
INSERT INTO area(id, id_sub_location, path) VALUES(53, 1, 'm104 435l13-20 20 10-10 22z');
INSERT INTO area(id, id_sub_location, path) VALUES(54, 1, 'm83 421l10-12 6 1 8-1 10 6-13 20z');
INSERT INTO area(id, id_sub_location, path) VALUES(55, 1, 'm65 405l16-16 9 9v6l3 5-10 12z');
INSERT INTO area(id, id_sub_location, path) VALUES(56, 1, 'm48 385l13-8 5 3 7 1 8 8-16 16z');
INSERT INTO area(id, id_sub_location, path) VALUES(57, 1, 'm35 365l19-11 6 10-1 7 2 6-13 8z');
INSERT INTO area(id, id_sub_location, path) VALUES(58, 1, 'm27 345l20-9 7 18-19 11z');
INSERT INTO area(id, id_sub_location, path) VALUES(59, 1, 'm27 345l-2-6 12-27h7l3 24z');
INSERT INTO area(id, id_sub_location, path) VALUES(60, 1, 'm702 338h31v12l-5 5-1 6h-24z');
INSERT INTO area(id, id_sub_location, path) VALUES(61, 1, 'm703 361h24l7 8v14h-30z');
INSERT INTO area(id, id_sub_location, path) VALUES(62, 1, 'm704 383h30v14l-5 4-1 4h-24z');
INSERT INTO area(id, id_sub_location, path) VALUES(63, 1, 'm704 405h24l1 3 6 5v13h-30z');
INSERT INTO area(id, id_sub_location, path) VALUES(64, 1, 'm705 426h30v15l-4 4-1 5h-24z');
INSERT INTO area(id, id_sub_location, path) VALUES(65, 1, 'm706 450h24l1 3 5 5v12h-30z');
INSERT INTO area(id, id_sub_location, path) VALUES(66, 1, 'm708 511h32v26h-32z');
INSERT INTO area(id, id_sub_location, path) VALUES(67, 1, 'm763 335h33v23h-24l-1-3-7-6z');
INSERT INTO area(id, id_sub_location, path) VALUES(68, 1, 'm771 362l1-4h24l1 23h-32v-13z');
INSERT INTO area(id, id_sub_location, path) VALUES(69, 1, 'm765 381h32l1 21h-26l-1-3-5-5z');
INSERT INTO area(id, id_sub_location, path) VALUES(70, 1, 'm771 407l1-5h26v24h-31v-15z');
INSERT INTO area(id, id_sub_location, path) VALUES(71, 1, 'm767 426h31l1 22h-26l-1-4-5-5z');
INSERT INTO area(id, id_sub_location, path) VALUES(72, 1, 'm771 452l2-4h26l1 22h-33v-14z');
INSERT INTO area(id, id_sub_location, path) VALUES(73, 1, 'm625 59h110v61c-20 0-68 29-68 76 0 4-5 7-9 7h-33z');
INSERT INTO area(id, id_sub_location, path) VALUES(74, 2, 'm97 56h288v274H136l-32-38h-7z');
INSERT INTO area(id, id_sub_location, path) VALUES(75, 2, 'm385 59h48v144h-15c-5 0-15 6-15 11h-18z');
INSERT INTO area(id, id_sub_location, path) VALUES(76, 2, 'm433 59h52v144h-52z');
INSERT INTO area(id, id_sub_location, path) VALUES(77, 2, 'm485 59h110v144H485z');
INSERT INTO area(id, id_sub_location, path) VALUES(78, 2, 'm625 59h110v61c-20 0-68 29-68 76 0 4-5 7-9 7h-33z');
INSERT INTO area(id, id_sub_location, path) VALUES(79, 2, 'm876 59v61H749V59z');
INSERT INTO area(id, id_sub_location, path) VALUES(80, 2, 'm876 59h56v61h-56z');
INSERT INTO area(id, id_sub_location, path) VALUES(81, 2, 'm1026 169h50v61l-50 12z');
INSERT INTO area(id, id_sub_location, path) VALUES(82, 2, 'm932 169h81v77l-81 22z');
INSERT INTO area(id, id_sub_location, path) VALUES(83, 2, 'm876 169h56v99l-56 15z');
INSERT INTO area(id, id_sub_location, path) VALUES(84, 2, 'm876 283l-113 30h-6l-3-3v-11h8l3-4 44-12c6-2 9-7 9-10l3-30c8-12 11-26 11-43v-17c0-11 3-14 12-14h32v59z');
INSERT INTO area(id, id_sub_location, path) VALUES(85, 2, 'm649 252h19c6 0 10 4 10 11v10c0 6 6 11 13 13l43 9 3 4h8v11l-3 3h-78l-12 12h-3z');
INSERT INTO area(id, id_sub_location, path) VALUES(86, 2, 'm593 252h56v73h-28v-53h-28z');
INSERT INTO area(id, id_sub_location, path) VALUES(87, 2, 'm593 272h28v53h-28z');
INSERT INTO area(id, id_sub_location, path) VALUES(88, 2, 'm530 252h52v73h-52z');
INSERT INTO area(id, id_sub_location, path) VALUES(89, 2, 'm448 252h82v73h-82z');
INSERT INTO area(id, id_sub_location, path) VALUES(90, 2, 'm385 241h18c0 5 9 11 15 11h30v73h-63z');
INSERT INTO area(id, id_sub_location, path) VALUES(91, 2, 'm595 59h30v144h-30z');
INSERT INTO area(id, id_sub_location, path) VALUES(92, 2, 'm932 59h46v61h-46z');
INSERT INTO area(id, id_sub_location, path) VALUES(93, 2, 'm978 59h35v61h-35z');
INSERT INTO area(id, id_sub_location, path) VALUES(94, 2, 'm1026 59h31v61h-31z');
INSERT INTO area(id, id_sub_location, path) VALUES(95, 2, 'm1057 59h19v61h-19z');
INSERT INTO area(id, id_sub_location, path) VALUES(96, 2, 'm1076 59h23v61h-23z');
INSERT INTO area(id, id_sub_location, path) VALUES(97, 2, 'm1099 59h28v61h-28z');
INSERT INTO area(id, id_sub_location, path) VALUES(98, 2, 'm1127 59h56v61h-56z');
INSERT INTO area(id, id_sub_location, path) VALUES(99, 2, 'm1183 59h45l10 61v24h-78c0-7 0-10-4-14v-10h27z');
INSERT INTO area(id, id_sub_location, path) VALUES(100, 2, 'm1160 144h78v43l-55 14v-32h-27v-11c3-3 4-8 4-14z');
INSERT INTO area(id, id_sub_location, path) VALUES(101, 2, 'm1127 169h56v32l-56 15z');
INSERT INTO area(id, id_sub_location, path) VALUES(102, 2, 'm1076 169h51v47l-51 14z');
INSERT INTO area(id, id_sub_location, path) VALUES(103, 3, 'm97 56h288v274H136l-32-38h-7z');
INSERT INTO area(id, id_sub_location, path) VALUES(104, 3, 'm385 59h61v144h-16c-6 0-15 6-15 11h-30z');
INSERT INTO area(id, id_sub_location, path) VALUES(105, 3, 'm446 59h64v144h-64z');
INSERT INTO area(id, id_sub_location, path) VALUES(106, 3, 'm510 59h109v144H510z');
INSERT INTO area(id, id_sub_location, path) VALUES(107, 3, 'm749 77h22v43h-22z');
INSERT INTO area(id, id_sub_location, path) VALUES(108, 3, 'm791 77V59h85v61H771V77z');
INSERT INTO area(id, id_sub_location, path) VALUES(109, 3, 'm876 59h28v61h-28z');
INSERT INTO area(id, id_sub_location, path) VALUES(110, 3, 'm904 59h28v61h-28z');
INSERT INTO area(id, id_sub_location, path) VALUES(111, 3, 'm932 121V59h296l10 60v68l-162 43v-72h-35c6 0 10-2 10-6v-13c0-4-4-6-10-6h-94c-9 0-15-6-15-12z');
INSERT INTO area(id, id_sub_location, path) VALUES(112, 3, 'm1002 158h74v72l-74 19z');
INSERT INTO area(id, id_sub_location, path) VALUES(113, 3, 'm932 170c0-6 5-12 14-12h56v91l-70 19z');
INSERT INTO area(id, id_sub_location, path) VALUES(114, 3, 'm876 169h56v99l-56 15z');
INSERT INTO area(id, id_sub_location, path) VALUES(115, 3, 'm844 169h32v59h-48c3-7 4-15 4-28v-17c0-11 3-13 12-14z');
INSERT INTO area(id, id_sub_location, path) VALUES(116, 3, 'm754 295h11l44-12c4-1 9-6 9-10l3-30 7-15h48v55l-113 30h-9z');
INSERT INTO area(id, id_sub_location, path) VALUES(117, 3, 'm654 252h15c5 0 9 6 9 12v9c0 6 6 11 13 13l43 9h11v18h-81l-12 12h-31v-29h33z');
INSERT INTO area(id, id_sub_location, path) VALUES(118, 3, 'm621 252h33v44h-33z');
INSERT INTO area(id, id_sub_location, path) VALUES(119, 3, 'm593 252h28v20h-28z');
INSERT INTO area(id, id_sub_location, path) VALUES(120, 3, 'm593 272h28v53h-28z');
INSERT INTO area(id, id_sub_location, path) VALUES(121, 3, 'm530 252h52v73h-52z');
INSERT INTO area(id, id_sub_location, path) VALUES(122, 3, 'm448 252h82v73h-82z');
INSERT INTO area(id, id_sub_location, path) VALUES(123, 3, 'm385 241h30c0 5 9 11 15 11h18v73h-63z');
INSERT INTO area(id, id_sub_location, path) VALUES(124, 3, 'm748 150c67 0 67 100 0 100-67 0-67-100 0-100z');
INSERT INTO area(id, id_sub_location, path) VALUES(125, 3, 'm625 59h166v18h-42v43h-14c-20 0-68 29-68 76 0 4-5 7-9 7h-33z');
