#!/usr/bin/env ruby
require 'bunny'

# conectarse a rabbitmq
# connection = Bunny.new(hostname: 'ipBroker') para conexiones en otra máquina
# connection = Bunny.new
connection = Bunny.new(hostname: 'rdp02.unizar.es', user: 'alumno', pass: 'alumno')
connection.start

# creamos un canal para la conexion 
channel = connection.create_channel

# para mandar necesitamos almacenar las cosas en una cola, y luego publicar esta


queue = channel.queue('task_as', durable: true)
message = ARGV.empty? ? 'Hello world!' : ARGV.join(' ')

queue.publish(message, persistent: true)

puts " [x] Sent #{message}"

# puts " [x] Sent 'Hello World!'"

connection.close