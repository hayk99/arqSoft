#!/usr/bin/env ruby
require 'bunny'

# conectarse a rabbitmq
# connection = Bunny.new(hostname: 'ipBroker') para conexiones en otra máquina
connection = Bunny.new(hostname: 'rdp02.unizar.es', user: 'alumno', pass: 'alumno')
connection.start

# creamos un canal para la conexion 
channel = connection.create_channel

# para mandar necesitamos almacenar las cosas en una cola, y luego publicar esta

queue = channel.queue('as')
channel.default_exchange.publish('Hayk ....', routing_key: queue.name)
# channel.default_exchange.publish('Carne picada', routing_key: queue.name)
# queue.publish('Hayk ....', persistent: true)

puts " [x] Sent 'Hayk!'"

connection.close