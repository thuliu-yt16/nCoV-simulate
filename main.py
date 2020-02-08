from tkinter import *
root = Tk()

cv = Canvas(root, bg='white')
cv.create_oval(10,10,10,10)
cv.pack()
root.mainloop()