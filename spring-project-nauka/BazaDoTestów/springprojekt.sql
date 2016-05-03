-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Czas generowania: 18 Sty 2016, 15:09
-- Wersja serwera: 10.1.9-MariaDB
-- Wersja PHP: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `springprojekt`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `lista`
--

CREATE TABLE `lista` (
  `id` int(11) NOT NULL,
  `id_uzytkownik` int(11) NOT NULL,
  `id_slowa` int(11) NOT NULL,
  `liczba_poprawnych` int(11) NOT NULL,
  `nauczone` tinyint(1) NOT NULL,
  `data` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `plan`
--

CREATE TABLE `plan` (
  `id` int(11) NOT NULL,
  `id_zestaw` int(11) NOT NULL,
  `id_uzytkownik` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `slownik`
--

CREATE TABLE `slownik` (
  `id` int(11) NOT NULL,
  `pl_slowo` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `en_slowo` varchar(255) NOT NULL,
  `en_zdanie` varchar(255) NOT NULL,
  `pl_zdanie` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `id_zestaw` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `slownik`
--

INSERT INTO `slownik` (`id`, `pl_slowo`, `en_slowo`, `en_zdanie`, `pl_zdanie`, `id_zestaw`) VALUES
(3, 'jeść', 'eat', 'I am eating dinner now.', 'Teraz jem posiłek.', 2),
(4, 'cześć', 'Hello', 'Hello firend.', 'Cześć stary.', 2),
(5, 'szczególnie', 'especially', 'She''s especially interested in American poetry.', 'On jest zainteresowana szczególnie poezją Amerykańską.', 2),
(7, 'umiejętność', 'ability', 'He’s got the ability to explain things clearly.', 'On posiada umiejętność jasnego tłumaczenia rzeczy.', 2),
(8, 'według, zgodnie z', 'according ', 'Everything went according to plan.', 'Wszystko poszło zgodnie z planem.', 2),
(9, 'pozwolić sobie na coś', 'afford', 'We can’t afford a new car.', 'Nie możemy pozwolić sobie na nowy samochód.', 2),
(10, 'ogłaszać', 'announce', 'They announced they were going to get married.', 'Ogłosili, że zamierzają się pobrać.', 2),
(11, 'sam', 'alone', 'Leave me alone!', 'Zostaw mnie samego!', 2),
(12, 'obóz', 'camp', 'We set up camp near the river.', 'Rozbiliśmy obóz niedaleko rzeki.', 2),
(13, 'mięso', 'meat', '', '', 3),
(14, 'drób', 'poultry', '', '', 3),
(15, 'indyk', 'turkey', '', '', 3),
(16, 'kurczak', 'chicken', '', '', 3),
(17, 'wołowina', 'beef', '', '', 3),
(18, 'ryba', 'fish', '', '', 3),
(19, 'karp', 'carp', '', '', 3),
(20, 'okoń', 'bass', '', '', 3),
(21, 'chleb', 'bread', '', '', 3),
(22, 'bułka', 'roll', '', '', 3),
(23, 'owoc', 'fruit', '', '', 3),
(24, 'dżem', 'jam', '', '', 3);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `uzytkownik`
--

CREATE TABLE `uzytkownik` (
  `id` int(8) NOT NULL,
  `user` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `login` tinyint(1) NOT NULL,
  `role` varchar(35) NOT NULL,
  `powtorki` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `uzytkownik`
--

INSERT INTO `uzytkownik` (`id`, `user`, `password`, `login`, `role`, `powtorki`) VALUES
(2, 'Dominik', 'qwerty', 1, 'ROLE_USER', 0),
(13, 'Hose', '12345', 1, 'ROLE_USER', 0),
(14, 'Domsad', 'asdasd', 1, 'ROLE_USER', 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `zestaw`
--

CREATE TABLE `zestaw` (
  `id` int(11) NOT NULL,
  `nazwa` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `zestaw`
--

INSERT INTO `zestaw` (`id`, `nazwa`) VALUES
(2, 'Podstawy'),
(3, 'Jedzenie'),
(4, 'Biznes'),
(5, 'programowanie');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `lista`
--
ALTER TABLE `lista`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_uzytkownik` (`id_uzytkownik`),
  ADD KEY `id_slowa` (`id_slowa`);

--
-- Indexes for table `plan`
--
ALTER TABLE `plan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_zestaw` (`id_zestaw`),
  ADD KEY `id_uzytkownik` (`id_uzytkownik`);

--
-- Indexes for table `slownik`
--
ALTER TABLE `slownik`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `uzytkownik`
--
ALTER TABLE `uzytkownik`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `zestaw`
--
ALTER TABLE `zestaw`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `lista`
--
ALTER TABLE `lista`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `plan`
--
ALTER TABLE `plan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `slownik`
--
ALTER TABLE `slownik`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
--
-- AUTO_INCREMENT dla tabeli `uzytkownik`
--
ALTER TABLE `uzytkownik`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT dla tabeli `zestaw`
--
ALTER TABLE `zestaw`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `lista`
--
ALTER TABLE `lista`
  ADD CONSTRAINT `lista_ibfk_1` FOREIGN KEY (`id_uzytkownik`) REFERENCES `uzytkownik` (`id`),
  ADD CONSTRAINT `lista_ibfk_2` FOREIGN KEY (`id_slowa`) REFERENCES `slownik` (`id`);

--
-- Ograniczenia dla tabeli `plan`
--
ALTER TABLE `plan`
  ADD CONSTRAINT `plan_ibfk_1` FOREIGN KEY (`id_zestaw`) REFERENCES `zestaw` (`id`),
  ADD CONSTRAINT `plan_ibfk_2` FOREIGN KEY (`id_uzytkownik`) REFERENCES `uzytkownik` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
