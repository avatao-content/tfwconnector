from os.path import dirname, realpath, join

from setuptools import setup, find_packages


here = dirname(realpath(__file__))

with open(join(here, 'requirements.txt'), 'r') as ifile:
    requirements = ifile.read().splitlines()

setup(name='tfwconnector',
      description='Connector tools for Avatao tutorial-framework',
      url='https://github.com/avatao-content/tfwconnector',
      author='Avatao.com Innovative Learning Kft.',
      author_email='support@avatao.com',
      license='custom',
      packages=find_packages('python3'),
      package_dir={'': 'python3'},
      install_requires=requirements,
      zip_safe=False)
