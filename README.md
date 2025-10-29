# Project-assignment
If you make a change in one block of the blockchain (for example, modifying the user ID or password), it affects the entire chain from that point forward.
Here’s why:
•	Each block’s hash depends on its contents and the previous block’s hash.
•	If you change anything in a block (like user data, password, or metadata), its hash value changes.
•	The next block stores the previous hash from the modified block. Now those two hashes don’t match, breaking the chain’s continuity and integrity.
•	In a real blockchain, it’s instantly obvious if a block has been tampered with, because every subsequent block’s hash would also become invalid unless you recompute all following hashes (which is computationally infeasible for large chains and secured with mechanisms like proof-of-work).
