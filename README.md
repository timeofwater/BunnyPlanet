# Bunny Planet

* version: java 1.11.8
* author: Shang Zemo

---

### map

there are ten planets on the map:

* .------------------------------------------------------.
* |&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;|
* |&emsp;&emsp;&emsp;0&emsp;&emsp;&emsp;3&emsp;------&emsp;5&emsp;&emsp;&emsp;7&emsp;&emsp;&emsp;|
* |&emsp;&emsp;&emsp;&emsp; - &emsp;-&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;-&emsp;-&emsp;&emsp;&emsp;&emsp;|
* |&emsp;&emsp;&emsp;&emsp;&emsp;1&emsp;&emsp; &emsp;&emsp;&emsp;&emsp;&emsp;8&emsp;&emsp;&emsp;&emsp; |
* |&emsp;&emsp;&emsp;&emsp; - &emsp;-&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;-&emsp;-&emsp;&emsp;&emsp;&emsp;|
* |&emsp;&emsp;&emsp;2&emsp;&emsp;&emsp;4&emsp;------&emsp;6&emsp;&emsp;&emsp;9&emsp;&emsp;&emsp;|
* |&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;|
* .------------------------------------------------------.

planets who are lined can transfer soldiers.

for example:

* [X]  planet_0 -> planet_1
* [ ]  planet_0 -> planet_2
* [X]  planet_0 -> planet_1 -> planet_2

---

### introduce

---

##### game:

how to play:

1. the server run the 'Server.jar' and then you get your ip address.
2. the client run the 'Client.jar' and then input the address appeared on your friend's server just now.
3. enter the simplest command '`0`' to test your game.
4. have fun in your game time.

how to retry:

* sorry, this action may be too hard to the author, you can restart the script.

why did I see '`server is not opening.`' on my client?

1. check your internet and ensure that you can visit website.
2. maybe your friend have not prepared the server yet.
3. wrong ip format, the ip format may like `xx.xx.xx.xx`
4. port has been occupied, this game uses port 6200, and it cannot be changed. it's too hard for the author.

can I share/edit it for myself?

* make a statement: `origin version by Shang Zemo`, and help yourself!

the game is so good, and I want to get in touch with the author:

* it's my pleasure to get this, you can visit [my website](http://zmbunny.com), or my WeChat public number: `½­ÖÜ`

---

##### planet:

planets have many properties:

* index: from 0 to 9, each planet has an index. **cannot change**
* energy: points to build soldiers or producers.
* camp number: the id flag
  * -1: the left player (usually server)
  * 0: the wild planet (anyone can capture it)
  * 1: the right player (usually client)
* producer: the energy point producer, give 1 energy per 3s per producer
* soldier: the soldier you can send to other planet after 3s, soldiers in different camp will battle.

---

##### producer and soldier

* **Don't Build All Soldiers And No Producers At The Beginning.**
* producer and soldier are all cost 10 energy.
* producer will turn the camp if you have captured this planet.
* if you have captured a planet, but there is no producer on it, the author will give you a producer by Easter Egg,
  because the author is an Easter Bunny! Just say thanks to bunnies.

---

### commands

---

##### get planet information:

`(index)`

* index: index of the planet

---

##### build:

`(index) / b (number) (form)`

* index: index of the planet
* number: how many do you want to build
* form:
  * '1' or 'p' or 'producer' means producer
  * '2' or 's' or 'soldier' means soldier

---

##### transfer:

`(index) / t (number) -> (target index)`

* index: index of the planet
* number: how many do you want to build
* target index: index of the target planet

---
