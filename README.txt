AI Assignment
Poker framework for the implementation of machine learning techniques

Features:
(18/02/12)
1. Card and deck implementations
2. Player generation from properties file
3. True game structure and dealing of appropriate cards
4. Poker hand checking
5. Comparators for Card and HandScore so as winners can be decided
6. Round implementation
7. Betting structure to the extent of token based blind progression
8. Metrics around card and hand frequencies (see sample data below)
9. Functioning game implementation

TODO:
1. Implement AI for several types of player
2. User controls for a playable experience
3. LAN capabilities

Usage:
1. Game.java drives the main application over 100 rounds (set in main method)
2. Testing package contains individual hand testing for win checks
3. Testing package contains game hand simulation for 6 players over (x) games

Sample data:

Winning hand frequencies in a 6 player game over 5 million iterations.

1562353 :	Two Pair
837587	:	Pair
750346	:	Straight
710550	:	Three of a Kind
543094	:	Full House
540344	:	Flush
44205	:	Four of a Kind
8172	:	Straight Flush
2807	:	High Card
542	:	Royal Flush

Written by Neil Paul (neilcpaul@gmail.com)