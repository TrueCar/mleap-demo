# Instruction for Running Notebook

In order to run the example notebook as seen in the Hadoop Conference Dublin live demo, follow these instructions.

## Setup Spark Jupyter Notebooks

### Virtualenv

I like to create a new virtualenv specifically for my notebooks, feel free to set this up however you like.

```
virtualenv mleap-notebooks # create the virtual env
cd mleap-notebooks
source bin/activate # activate the virtualenv
```

### Install Jupyter

Next we need to install Jupyter.

```
pip install jupyter
```

### Install the [Toree kernel](https://github.com/apache/incubator-toree)

The Toree kernel allows us to write Scala and talk with Spark from Jupyter.

```
pip install toree
jupyter toree install
```

### Install the MLeap Spark Jupyter Kernel

You will find a folder called mleap-spark under kernels, copy this folder to your home ipython directory. After doing this step, you will have access to a Spark kernel that automatically loads all of the MLeap jars via Spark Packages.

```
cp -r kernels/mleap-spark ~/.ipython/kernels
```

### Run the Notebook

Now let's load up our notebook into Jupyter!

```
cd notebooks # from the root of mleap-demo
jupyter notebook # this will start a web ui
```

After the web UI starts, select the notebook you want to run and have fun :)

# MLeap Demo Without Jupyter

If you are following along with the blog on [Driven by Code](https://drivenbycode.com) or just don't want to use Jupyter, then you can find the instructions for how to use this project in the file in this repository: blog/mleap.md.

# License

The MIT License (MIT)
Copyright (c) 2016 TrueCar

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

