# Server Pinger Fixer

This mod improves how servers in the multiplayer list are pinged, making for a much nicer experience in the menu. You can especially notice the difference when pressing the refresh button a lot. 

The mod works by increasing the Thread count used for pinging servers. It also clears the Thread pool when it's overloaded.

| Without the Mod                                                                                            | With the Mod                                                                                                |
|------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|
| Gets stuck loading after just a few times<br/> pressing the refresh button                                 | Loads even when spamming the refresh button                                                                 |
| <img src="./images/withoutMod.gif" alt="gif of the issue without the mod" width="300" style="float:left"/> | <img src="./images/withMod.gif" alt="gif of the issue fixed with the mod" width="300" style="float:right"/> |

<br>

Inspired by: https://github.com/Nixuge/ServerlistBufferFixer
